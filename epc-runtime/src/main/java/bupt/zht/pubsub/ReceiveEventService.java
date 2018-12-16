package bupt.zht.pubsub;

import bupt.zht.EpcObject;
import bupt.zht.ProcessInfo;
import bupt.zht.activity.*;
import javax.jws.WebService;
import java.util.List;
import java.util.Map;

@WebService(endpointInterface="org.apache.servicemix.wsn.push.INotificationProcess",
        serviceName="INotificationProcess")
public class ReceiveEventService implements INotificationProcess {

    private Map<Function, LogicTreeNode> functionLogicTreeMap;
    private Map<Event, Function> eventFunctionMap;
    private List<Event> eventList;
    private UpdateLogicTree updateLogicTree;

    public ReceiveEventService() {
        functionLogicTreeMap = ProcessInfo.epcCompiler.getFunctionMap();
        eventFunctionMap = ProcessInfo.epcCompiler.getEventFunctionMap();
        eventList = ProcessInfo.epcCompiler.getEpcEventList();
        updateLogicTree = new UpdateLogicTree();
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
            if(theme.equals(e.getTheme())){
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
            System.out.println("找到函数对应的逻辑表达树节点是一个事件： " + ((Event) rootObject).getName());
            updateLogicTree.executeFunction(aimFunction,message);
            return;
        }
        // 否则更新逻辑表达树
        updateLogicTree.updateFunctionTree(root,event,aimFunction,message);
    }
}