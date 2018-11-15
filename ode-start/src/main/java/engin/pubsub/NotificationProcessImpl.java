package engin.pubsub;

import javax.jws.WebService;

/**
 * @author zhanghangting
 * @date 2018/10/21 18:37
 */
@WebService(endpointInterface="org.apache.servicemix.wsn.push.INotificationProcess",
        serviceName="INotificationProcess")
public class NotificationProcessImpl implements INotificationProcess {

    public void notificationProcess(String notification) {

        // 收到开始事件消息，创建一个流程实例
        if("arriveEvent".equals(notification)){

        }
        System.out.println("get the message from publisher: " + notification);
    }
}
