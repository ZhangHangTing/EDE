package bupt.zht.pubsub;

import bupt.zht.EpcObject;
import bupt.zht.ProcessInfo;
import bupt.zht.activity.*;
import bupt.zht.service.Service;
import bupt.zht.service.ServiceFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class UpdateLogicTree implements INotificationProcess {

    private Map<Function, LogicTreeNode> functionLogicTreeMap;
    private Map<Event, Function> eventFunctionMap;
    private Map<LogicUnit, Queue<EpcObject>> logicUnitObjectMap;
    private List<Event> eventList;

    public UpdateLogicTree() {
        functionLogicTreeMap = ProcessInfo.epcCompiler.getFunctionMap();
        eventFunctionMap = ProcessInfo.epcCompiler.getEventFunctionMap();
        eventList = ProcessInfo.epcCompiler.getEpcEventList();
        logicUnitObjectMap = ProcessInfo.epcCompiler.getLogicUnitObjectMap();
    }
    // 发布订阅系统中这里会将所有的订阅事件消息发送过来，如果同时发送多个不同主题的消息，可能会出现逻辑事件树数据不一致的情况
    // 这里需要对接受到的消息进行加锁或者放到阻塞队列中 操作。
    // 目前演示的时候，我们一次只发送一个事件消息，并不会出现同步的问题
    @Override
    public void notificationProcess(String notification) {
        if(notification == null){
            return;
        }
        // 接受到的数据无法判断是从哪个主题中发送过来的，只能约定发送的数据的格式
        String[] data = notification.split(";");
        String theme = data[0];
        String message = data[1];
        Function aimFunction = null;
        Event event = null;
        // 通过主题找到事件节点,通过事件节点找到函数节点
        for (Event e : eventList) {
            if(theme.equals(event.getName())){
                event = e;
                aimFunction = eventFunctionMap.get(event);
                break;
            }
        }
        if(aimFunction == null || event == null) {
            return;
        }
        LogicTreeNode root = functionLogicTreeMap.get(aimFunction);
        // 获取函数对应的逻辑表达树
        EpcObject rootObject = root.getEpcObject();
        // 如果逻辑表达树节点是表示的是一个事件，则直接执行
        if(rootObject instanceof Event){
            executeFunction(aimFunction,message);
            return;
        }
        // 否则更新逻辑表达树
        updateFunctionTree(root,event,aimFunction,message);
    }
    public void updateFunctionTree(LogicTreeNode root, Event event,Function function,String message) {
        EpcObject rootObject = root.getEpcObject();
        // 最多需要循环每一个逻辑节点的状态变化
        int loopNum = logicUnitObjectMap.size();
        for(int i = 0; i < loopNum; i++){
            for(Map.Entry<LogicUnit,Queue<EpcObject>> map : logicUnitObjectMap.entrySet()){
                LogicUnit logicUnit = map.getKey();
                Queue<EpcObject> epcObjects = map.getValue();
                // 找到队列中相同的事件节点，才需要更新逻辑节点，更新了逻辑节点，才需要更新其他的节点
                if(epcObjects.contains(event)){
                    if(logicUnit instanceof And){
                        if(((And) logicUnit).isFirstComing()){
                            ((And) logicUnit).setFirstComing(true);
                        } else{
                            ((And) logicUnit).setNextComing(true);
                        }
                    }else{
                        logicUnit.setAlive(true);
                    }
                }
            }
        }
        // 更新完Map中所有的逻辑节点状态以后，只需要将所有函数的root节点和该Map中的逻辑节点相匹配即可
        for(Map.Entry<LogicUnit,Queue<EpcObject>> map : logicUnitObjectMap.entrySet()){
            LogicUnit logicUnit = map.getKey();
            if(logicUnit.equals(rootObject) && logicUnit.isAlive()){
                executeFunction(function,message);
            }
        }
        System.out.print(message + "消息到达，执行一次逻辑事件表达树更新操作");
    }
    // 如何执行该函数是关键
    public void executeFunction(Function function, String message){
        Service executeService = ServiceFactory.getServiceInstance(function.getServiceName(),message,"");
        executeService.run();
    }
}
