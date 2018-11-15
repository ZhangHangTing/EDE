package bupt.zht;

import bupt.zht.activity.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import sun.rmi.runtime.Log;
import sun.security.jgss.LoginConfigImpl;

import java.io.File;
import java.util.*;

/**
 * @author zhanghangting
 * @date 2018/10/30 15:37
 */
public class EpcCompiler {

    private List<Event> epcEventList;
    private List<Function> epcFunctionList;
    private List<LogicUnit> epcLogicList;
    private List<Flow> epcFlowList;
    private Map<String, EpcObject> epcMap;
    private Map<Function, LogicTreeNode> functionMap;
    private Map<LogicUnit, Queue<EpcObject>> logicUnitEventsMap;

    public EpcCompiler() {
        init();
    }

    public void init() {
        epcEventList = new ArrayList<>();
        epcFunctionList = new ArrayList<>();
        epcLogicList = new ArrayList<>();
        epcFlowList = new ArrayList<>();
        epcMap = new HashMap<>();
        functionMap = new HashMap<>();
        logicUnitEventsMap = new HashMap<>();
    }

    // 第一步编译，将EPML文档解析成DOM对象，存入链表和Map中
    public void compile(String epmlFile) throws DocumentException {

        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(epmlFile));
        Element epmlNode = document.getRootElement();
        Element epcNode = epmlNode.element("epc");

        List<Element> eventList = epcNode.elements("event");
        List<Element> functionList = epcNode.elements("function");
        List<Element> andList = epcNode.elements("and");
        List<Element> xorList = epcNode.elements("xor");
        List<Element> orList = epcNode.elements("or");
        List<Element> arcList = epcNode.elements("arc");
        List<Element> flowList = new ArrayList<>();
        for (Element arc : arcList) {
            flowList.add(arc.element("flow"));
        }
        for (Element event : eventList) {
            String eventId = event.attribute("id").getValue();
            String eventName = event.element("name").getText();
            String eventTheme = event.attribute("Theme").getValue();
            Event eventObject = new Event(eventId, eventName);
            eventObject.setTheme(eventTheme);
            epcEventList.add(eventObject);
            epcMap.put(eventId, eventObject);
        }
        for (Element function : functionList) {
            String functionId = function.attribute("id").getValue();
            String functionName = function.element("name").getText();
            String functionServiceName = function.attribute("ServiceName").getValue();
            Function functionObject = new Function(functionId, functionName);
            functionObject.setServiceName(functionServiceName);
            epcFunctionList.add(functionObject);
            epcMap.put(functionId, functionObject);
        }
        for (Element and : andList) {
            String andId = and.attribute("id").getValue();
            And andObject = new And(andId, "and");
            epcLogicList.add(andObject);
            epcMap.put(andId, andObject);
        }
        for (Element or : orList) {
            String orId = or.attribute("id").getValue();
            Or orObject = new Or(orId, "or");
            epcLogicList.add(orObject);
            epcMap.put(orId, orObject);
        }
        for (Element xor : xorList) {
            String xorId = xor.attribute("id").getValue();
            Xor xorObject = new Xor(xorId, "xor");
            epcLogicList.add(xorObject);
            epcMap.put(xorId, xorObject);
        }
        for (Element flow : flowList) {
            String source = flow.attribute("source").getValue();
            String target = flow.attribute("target").getValue();
            Flow flowObject = new Flow(source, target);
            epcFlowList.add(flowObject);
        }
    }

    // 映射逻辑节点对应的事件或逻辑节点
    public void mappingLogicEvent() {
        Queue<EpcObject> tempLogicEvents = new LinkedList<>();
        for (LogicUnit logicUnit : epcLogicList) {
            for (Flow flow : epcFlowList) {
                // 找到了指向该逻辑节点到Flow
                if (logicUnit.getId().equals(flow.getTarget())) {
                    // 查询到指向逻辑节点到事件节点ID
                    String sourceEpcObjectId = flow.getSource();
                    // 获取该Epc节点，存入临时事件节点链表中,节点可能是事件或者逻辑节点
                    tempLogicEvents.add(epcMap.get(sourceEpcObjectId));
                }
            }
            logicUnitEventsMap.put(logicUnit,new LinkedList<>(tempLogicEvents));
            tempLogicEvents.clear();
        }
    }
    // 第二步，抽取函数与事件之间的关系，最终表示成一个函数对应一个事件或者一棵逻辑事件表达树
    public void extract() {
        for (Function function : epcFunctionList) {
            String target = function.getId();
            LogicTreeNode root = null;
            // 可能有多个source指向同一个target，所以这里需要遍历完整个flow，这也是为什么不用map的原因
            for (Flow flow : epcFlowList) {
                if (flow.getTarget().equals(target)) {
                    // 当根据函数ID找到source以后
                    String source = flow.getSource();
                    // 获得source对象
                    EpcObject tempSource = epcMap.get(source);
                    if (tempSource instanceof Ou) {
                        // 将Ou单元设置到Function中
                    }
                    if (tempSource instanceof Iu) {
                        // 将Iu单元设置到Function中
                    }
                    // 如果指向函数的对象是一个事件，那么该函数只可能有一个事件，函数对应一个事件的事件树
                    if (tempSource instanceof Event) {
                        root = new LogicTreeNode(tempSource);
                    }
                    // 如果指向函数的对象是一个逻辑表达式，那么该函数对应一棵逻辑表达事件树
                    if (tempSource instanceof LogicUnit) {
                        // 保存一个Map，保存逻辑表达节点对应的多个事件，这个工作已经在编译的第一步完成
                        // 从Map中查询逻辑节点与事件之间的关系，利用层级创建二叉树的方法，创建一棵逻辑事件表达树
                        Queue<EpcObject> childNodeQueue = new LinkedList<>();
                        Queue<EpcObject> fatherNodeQueue = new LinkedList<>();
                        fatherNodeQueue.add(tempSource);
                        childNodeQueue.addAll(logicUnitEventsMap.get(tempSource));

                        EpcObject lChildNode;
                        EpcObject fatherNode;
                        EpcObject rChildNode;
                        LogicTreeNode index = null;
                        // 层级创建二叉树的过程，用到了孩子队列和父亲队列
                        while (!childNodeQueue.isEmpty()) {
                            fatherNode = fatherNodeQueue.poll();
                            if(index == null){
                                index = new LogicTreeNode(fatherNode);
                            }else{
                                // 通过遍历的方法寻找到当前出队节点在已经构建树中的位置，并且指向它
                                index = findIndexTreeNode(root,fatherNode.getId());
                            }
                            if (root == null) {
                                root = index;
                            }
                            lChildNode = childNodeQueue.poll();
                            index.setLeft(new LogicTreeNode(lChildNode));
                            fatherNodeQueue.add(lChildNode);
                            if (logicUnitEventsMap.containsKey(lChildNode)){
                                childNodeQueue.addAll(logicUnitEventsMap.get(lChildNode));
                                System.out.println("左子树入队以后，输出现在孩子队列的节点");
                                for(EpcObject child : childNodeQueue){
                                    System.out.println(child.getName());
                                }
                            }
                            if (!childNodeQueue.isEmpty()) {
                                rChildNode = childNodeQueue.poll();
                                index.setRight(new LogicTreeNode(rChildNode));
                                fatherNodeQueue.add(rChildNode);
                                if (logicUnitEventsMap.containsKey(rChildNode)){
                                    childNodeQueue.addAll(logicUnitEventsMap.get(rChildNode));
                                    System.out.println("右子树入队以后，输出现在孩子队列的节点");
                                    for(EpcObject child : childNodeQueue){
                                        System.out.println(child.getName());
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
            }
            functionMap.put(function, root);
        }
    }
    public LogicTreeNode findIndexTreeNode(LogicTreeNode root,String indexId){
        Queue<LogicTreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            LogicTreeNode tempTreeNode = queue.poll();
            if(tempTreeNode.getEpcObject().getId().equals(indexId))
                return tempTreeNode;
            queue.add(tempTreeNode.getLeft());
            queue.add(tempTreeNode.getRight());
        }
        return null;
    }
    public void showLogicUnitEventsMap() {
        for (Map.Entry<LogicUnit, Queue<EpcObject>> map : logicUnitEventsMap.entrySet()) {
            Queue<EpcObject> list = map.getValue();
            System.out.print(map.getKey() + "：");
            for (EpcObject li : list) {
                System.out.print(li.getName() + "；");
            }
            System.out.println();
        }
    }
    public void showFunctionLogicTree() {
        for (Map.Entry<Function, LogicTreeNode> map : functionMap.entrySet()) {
            System.out.print(map.getKey().getName() + " 前序遍历：");
            preOrderVisit(map.getValue());
            System.out.println();
        }
    }
    public void preOrderVisit(LogicTreeNode root) {
        if (root != null) {
            System.out.print(root.getEpcObject().getName() + " ");
            preOrderVisit(root.getLeft());
            preOrderVisit(root.getRight());
        }
    }
    public static void main(String[] args) throws DocumentException {
        EpcCompiler epcCompiler = new EpcCompiler();
        epcCompiler.compile("C:\\Users\\lenovo\\Desktop/EPMLTest.xml");
        //epcCompiler.compile("C:\\Users\\lenovo\\Desktop/ComplexEventsTest.xml");
        epcCompiler.mappingLogicEvent();
        epcCompiler.showLogicUnitEventsMap();
        epcCompiler.extract();
        epcCompiler.showFunctionLogicTree();
//        while (true) {
//            System.out.println("running");
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
