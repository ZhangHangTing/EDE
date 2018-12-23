package bupt.zht.manage;

import bupt.zht.EpcCompiler;
import bupt.zht.EpcObject;
import bupt.zht.ProcessInfo;
import bupt.zht.activity.*;
import bupt.zht.process.ProcessInstance;
import bupt.zht.process.ProcessModel;
import bupt.zht.pubsub.UpdateLogicTree;
import bupt.zht.service.Service;
import bupt.zht.service.ServiceFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Map;
import java.util.Queue;
/**
 * @author zhanghangting
 * @date 2018/12/15 17:23
 */
    // 这里通过解析事件传递过来的schema文件，来确定该消息事件属于哪一个流程实例，或者是需要创建一个流程实例
    // 在流程管理类中，一个流程模型管理管理多个流程实例，需要在这里对这些数据进行管理，
    // 能够提供外部的接口对这些流程实例进行增删改查
public class ProcessManager {

    // private ProcessModel processModel;
//    private ProcessInstance processInstance;

    public ProcessManager(){
//        this.processModel = processModel;
    }
    public void execute(ProcessModel processModel,String message){
        try {
            parseSchema(processModel,message);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    // 当事件到达，对应的schema消息到达的时候，进行解析,找到这个事件对应的流程模型和流程实例
    public void parseSchema(ProcessModel processModel,String schemaFile) throws DocumentException {

        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(schemaFile));
        Element rootNode = document.getRootElement();
        // 获取流程模型和流程实例
        Element model = rootNode.element("model");
        String modelId = model.getText();
        Element instance = rootNode.element("instance");
        String instanceId = instance.getText();
        Element themeNode = rootNode.element("theme");
        String theme = themeNode.getText();
        Element messageNode = rootNode.element("message");
        String message = messageNode.getText();
        // 消息的schema如果表示的是开始事件，那么创建对应流程模型的一个流程实例
        if("start".equals(message)){
            ProcessInstance processInstance = createProcessInstance(processModel,modelId,instanceId);
            processModel.getProcessInstancesList().add(processInstance);
            System.out.println("创建了ID为：" + instanceId + "的流程实例流程,打印当前ProcessInfo中的流程实例信息");
            for (ProcessInstance tempInstance : processModel.getProcessInstancesList()) {
                System.out.println(tempInstance.getProcessInstanceId());
            }
            judgeRootEvent(processInstance,theme,processInstance.getProcessInstanceId());
        }else {// 否则，找到流程实例，更新这个流程实例的运行状态
            // 第一步： 根据流程模型ID和流程实例ID找到流程实例
            ProcessInstance processInstance = null;
            for(ProcessInstance pInstance : processModel.getProcessInstancesList()){
                if(instanceId.equals(pInstance.getProcessInstanceId())){
                    processInstance = pInstance;
                }
            }
            if(processInstance == null){
                System.out.println("无法找到ID为： " + instanceId + " 的流程实例");
                return;
            }
            System.out.println("找到ID为： " + processInstance.getProcessInstanceId() + " 的流程实例，接下来更新该实例的流程运行状态。。。。");

            judgeRootEvent(processInstance,theme,processInstance.getProcessInstanceId());
//            // 第二步：更新找到的流程实例的运行状态。
//            EpcCompiler modelCompier = processInstance.getEpcCompiler();
//            Event event = null;
//            Function function = null;
//
//            for (Event e : modelCompier.getEpcEventList()) {
//                if (theme.equals(e.getTheme())) {
//                    event = e;
//                    function = modelCompier.getEventFunctionMap().get(event);
//                    break;
//                }
//            }
//            if(event == null || function == null){
//                return;
//            }
//            LogicTreeNode root = modelCompier.getFunctionMap().get(function);
//            // 获取函数对应的逻辑表达树
//            EpcObject rootObject = root.getEpcObject();
//            // 如果逻辑表达树节点是表示的是一个事件，则直接执行
//            if (rootObject instanceof Event) {
//                System.out.println("找到函数对应的逻辑表达树节点是一个事件： " + ((Event) rootObject).getName());
//                UpdateLogicTree.executeFunction(function, message);
//                return;
//            }
//            // 触发已有的流程实例，更新流程实例状态
//            updateProcessInstance(root, event, function, message,processInstance);
        }
    }
    public ProcessInstance createProcessInstance(ProcessModel processModel,String processModelID,String processInstanceID){
        EpcCompiler epcCompiler = processModel.getEpcCompiler();
        ProcessInstance processInstance = new ProcessInstance(processModelID,processInstanceID,epcCompiler);
        return processInstance;
    }
    public void judgeRootEvent(ProcessInstance processInstance,String theme,String message){

        EpcCompiler modelCompier = processInstance.getEpcCompiler();
        Event event = null;
        Function function = null;
        for (Event e : modelCompier.getEpcEventList()) {
            if (theme.equals(e.getTheme())) {
                event = e;
                function = modelCompier.getEventFunctionMap().get(event);
                break;
            }
        }
        if(event == null || function == null){
            return;
        }
        LogicTreeNode root = modelCompier.getFunctionMap().get(function);
        // 获取函数对应的逻辑表达树
        EpcObject rootObject = root.getEpcObject();
        // 如果逻辑表达树节点是表示的是一个事件，则直接执行
        if (rootObject instanceof Event) {
            System.out.println("找到函数对应的逻辑表达树节点是一个事件： " + ((Event) rootObject).getName());
            UpdateLogicTree.executeFunction(function, message);
            return;
        }
        // 触发已有的流程实例，更新流程实例状态
        updateProcessInstance(root, event, function, message,processInstance);
    }
    public void updateProcessInstance(LogicTreeNode root, Event event, Function function, String message,ProcessInstance processInstance) {
        // 目前他们共同引用同一个编译器对象，导致多线程的问题！！！！！！！！！！
        Map<LogicUnit, Queue<EpcObject>> logicUnitObjectMap = processInstance.getEpcCompiler().getLogicUnitObjectMap();;
        EpcObject rootObject = root.getEpcObject();
        // 最多需要循环每一个逻辑节点的状态变化
        // int loopNum = logicUnitObjectMap.size();
        // for (int i = 0; i < loopNum; i++) {
            for (Map.Entry<LogicUnit, Queue<EpcObject>> map : logicUnitObjectMap.entrySet()) {
                LogicUnit logicUnit = map.getKey();
                Queue<EpcObject> epcObjects = map.getValue();
                // 找到队列中相同的事件节点，才需要更新逻辑节点，更新了逻辑节点，才需要更新其他的节点
                if (epcObjects.contains(event)) {
                    System.out.println("找到了逻辑节点对应的触发事件节点：" + event.getName());
                    if (logicUnit instanceof And) {
                        if (((And) logicUnit).isFirstComing()) {
                            System.out.println("And节点的第二个事件到达！");
                            ((And) logicUnit).setNextComing(true);
                        } else {
                            System.out.println("And节点的第一个事件到达！");
                            ((And) logicUnit).setFirstComing(true);
                        }
                    } else {
                        logicUnit.setAlive(true);
                    }
                }
//            }
        }
        // 更新完Map中所有的逻辑节点状态以后，只需要将所有函数的root节点和该Map中的逻辑节点相匹配即可
        for (Map.Entry<LogicUnit, Queue<EpcObject>> map : logicUnitObjectMap.entrySet()) {
            LogicUnit logicUnit = map.getKey();
            if (logicUnit.equals(rootObject) && logicUnit.isAlive()) {
                executeFunction(function, message);
            }
        }
        System.out.println("消息到达，更新逻辑事件表达树");
    }
    // 如何执行该函数是关键
    public void executeFunction(Function function, String message){
        Service executeService = ServiceFactory.getServiceInstance(function.getServiceName());
        executeService.run(message);
    }
}
