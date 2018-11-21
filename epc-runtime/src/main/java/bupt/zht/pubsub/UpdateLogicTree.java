package bupt.zht.pubsub;

import bupt.zht.ProcessInfo;
import bupt.zht.activity.Function;
import bupt.zht.activity.LogicTreeNode;
import java.util.Map;

public class UpdateLogicTree implements INotificationProcess{

    private Map<Function, LogicTreeNode> functionLogicTreeMap;
    // 发布订阅系统中这里会将所有的订阅事件消息发送过来，如果同时发送多个不同主题的消息，可能会出现逻辑事件树数据不一致的情况
    // 这里需要对接受到的消息进行加锁或者放到阻塞队列中 操作。
    // 目前演示的时候，我们一次只发送一个事件消息，并不会出现同步的问题
    @Override
    public void notificationProcess(String notification) {
        // 接受到的数据无法判断是从哪个主题中发送过来的，只能约定发送的数据的格式
        String[] data = notification.split(";");
        String theme = data[0];
        String message = data[1];
        updateFunctionTree(theme);
    }
    public void updateFunctionTree(String theme){
        Map<Function,LogicTreeNode> functionLogicTreeNodeMap = ProcessInfo.epcCompiler.getFunctionMap();
        for(int i = 0; i < functionLogicTreeNodeMap.size(); i++){
            LogicTreeNode root = functionLogicTreeNodeMap.get(i);
        }
    }
    public void driveFunction(Function function){
    }
}
