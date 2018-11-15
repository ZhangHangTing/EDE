package engin.service;

import engin.CheckStaff;
import engin.pubsub.INotificationProcess;
import engin.pubsub.Publisher;

import javax.jws.WebService;

/**
 * @author zhanghangting
 * @date 2018/10/29 16:52
 */
@WebService(endpointInterface="org.apache.servicemix.wsn.push.INotificationProcess",
        serviceName="INotificationProcess")
public class ShoppingCheckService implements INotificationProcess{
    @Override
    public void notificationProcess(String notification) {
        // 审核通过
        // 触发货物审核事件
        String[] dataSource = notification.split(";");
        String name = dataSource[0];
        String message = dataSource[1];
        CheckStaff checkStaff = new CheckStaff(name);
        checkStaff.doSomething(message);

    }
}
