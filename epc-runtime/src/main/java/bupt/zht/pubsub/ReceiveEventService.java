package bupt.zht.pubsub;

import bupt.zht.ProcessInfo;
import bupt.zht.manage.ProcessManager;
import bupt.zht.process.ProcessModel;
import javax.jws.WebService;

// 项目监听所有流程事件的消息主题，这里应该负责转发。将消息分发给该消息对应的流程模型中去。
@WebService(endpointInterface="org.apache.servicemix.wsn.push.INotificationProcess",
        serviceName="INotificationProcess")
public class ReceiveEventService implements INotificationProcess {

    public ReceiveEventService() {}
    // 发布订阅系统中这里会将所有的订阅事件消息发送过来，如果同时发送多个不同主题的消息，可能会出现逻辑事件树数据不一致的情况
    // 这里需要对接受到的消息进行加锁或者放到阻塞队列中 操作。
    // 目前演示的时候，我们一次只发送一个事件消息，并不会出现同步的问题
    @Override
    public void notificationProcess(String notification) {
        if(notification == null){
            return;
        }
        // 消息中需要传递对应流程模型的ID
        String[] message = notification.split(";");
        String processModelID = message[0];
        String schemaFilePath = message[1];
        for(ProcessModel processModel : ProcessInfo.processModelList){
            if(processModelID.equals(processModel.getProcessModelId())){
                // 根据流程模型ID找到流程模型，将消息体和对应流程模型交给管理器处理
                // ProcessManager应该是多线程的任务处理器
                new ProcessManager(processModel).parseSchema(schemaFilePath);
            }
        }
    }
}
