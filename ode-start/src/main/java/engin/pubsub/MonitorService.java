package engin.pubsub;

import javax.jws.WebService;

/**
 * @author zhanghangting
 * @date 2018/10/21 21:42
 */
@WebService(endpointInterface="org.apache.servicemix.wsn.push.INotificationProcess",
        serviceName="INotificationProcess")
public class MonitorService implements INotificationProcess {
    private static String eventName;
    @Override
    public void notificationProcess(String notification) {
        eventName = notification;
    }
    public static String getEventName(){
        return eventName;
    }
}
