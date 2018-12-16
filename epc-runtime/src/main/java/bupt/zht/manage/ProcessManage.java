package bupt.zht.manage;

import bupt.zht.EpcObject;
import bupt.zht.ProcessInfo;
import bupt.zht.activity.Event;
import bupt.zht.activity.Function;
import bupt.zht.activity.LogicTreeNode;
import bupt.zht.process.ProcessInstance;
import bupt.zht.process.ProcessModel;
import bupt.zht.pubsub.UpdateLogicTree;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhanghangting
 * @date 2018/12/15 17:23
 */
    // 这里通过解析事件传递过来的sechme文件，来确定该消息事件属于哪一个流程实例，或者是需要创建一个流程实例
    // 在流程管理类中，一个流程模型管理管理多个流程实例，需要在这里对这些数据进行管理，
    // 能够提供外部的接口对这些流程实例进行增删改查
public class ProcessManage {

    private Map<ProcessModel,List<ProcessInstance>> processModelListMap;

    // 当事件到达，对应的schema消息到达的时候，进行解析,找到这个事件对应的流程模型和流程实例
    public void parseSchema(String schemaFile) throws DocumentException {

        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(schemaFile));
        Element processNode = document.getRootElement();
        // 获取流程模型和流程实例
        Element model = processNode.element("model");
        String modelMark = model.getText();
        Element instance = processNode.element("instance");
        String instanceMark = instance.getText();
        Element messageNode = processNode.element("message");
        String message = messageNode.getText();
        Element eventNode = processNode.element("event");
        String eventName = eventNode.getText();
        Event event = (Event) ProcessInfo.epcCompiler.getEpcMap().get(eventName);
        Function function = ProcessInfo.epcCompiler.getEventFunctionMap().get(event);
        LogicTreeNode root = ProcessInfo.epcCompiler.getFunctionMap().get(function);
        // 获取事件消息
        if("start".equals(messageNode)){
            createProcessInstance(modelMark,instanceMark);
        }else{
            // 触发已有的流程实例，更新流程实例状态
            updateProcessInstance(modelMark,instanceMark,root,event,function,message);
        }
    }
    public ProcessInstance createProcessInstance(String modelMark,String instanceMark){
        // 第一步：通过modelMark获取流程模型
        ProcessModel processModel = ProcessInfo.processModelMap.get(modelMark);
        // 第二步：通过流程模型创建流程实例
        List<EpcObject> modelEpcOject = processModel.getModelEpcObjectList();
        List<Function> instanceFunction = new ArrayList<>();
        for(EpcObject object : modelEpcOject){
            if(object instanceof Function){
                instanceFunction.add((Function) object);
            }
        }
        ProcessInstance processInstance = new ProcessInstance(modelMark,instanceMark,instanceFunction);
        ProcessInfo.processInstanceMap.put(modelMark + instanceMark,processInstance);
        return processInstance;
    }
    public void updateProcessInstance(String modelMark,String instanceMark,LogicTreeNode root,
                                      Event event,Function aimFunction,String message){
        // 通过模型标志和实例标志来找到该流程实例
        ProcessInstance processInstance = ProcessInfo.processInstanceMap.get(modelMark + instanceMark);
        new UpdateLogicTree().updateFunctionTree(root,event,aimFunction,message);
    }
}
