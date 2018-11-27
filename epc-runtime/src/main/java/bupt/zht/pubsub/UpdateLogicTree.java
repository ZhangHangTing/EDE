package bupt.zht.pubsub;

import bupt.zht.EpcObject;
import bupt.zht.ProcessInfo;
import bupt.zht.activity.Event;
import bupt.zht.activity.Function;
import bupt.zht.activity.LogicTreeNode;
import bupt.zht.activity.LogicUnit;

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
        logicUnitObjectMap = ProcessInfo.epcCompiler.getLogicUnitEventsMap();
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
        updateFunctionTree(root,event,message);
    }
    public void updateFunctionTree(LogicTreeNode root, Event event,String message) {
        EpcObject rootObject = root.getEpcObject();
        if(rootObject instanceof Event){
            //driveFunction();
            return;
        }
        if(rootObject instanceof LogicUnit){
            // 如果根节点是逻辑节点，则这是一棵逻辑事件表达树
            // 事件更新父节点的逻辑节点状态，如果逻辑节点满足状态，则再更新逻辑节点的父节点，如此循环，知道遇到root节点
            // 如果root节点满足条件，则触发函数
            // 需要有一个map，找到event的父节点logicUnit
            // 也需要一个map，找到logicUnit的父节点logicUnit
            // logicUnitObjectMap
            // 事件从叶子节点不断更新到根节点
        }
    }
    public void driveFunction(Function function) {
    }
}
