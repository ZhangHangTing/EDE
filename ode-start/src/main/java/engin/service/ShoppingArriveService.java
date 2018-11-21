package engin.service;

import engin.DeliveryStaff;
import engin.pubsub.INotificationProcess;

import javax.jws.WebService;

/**
 * @author zhanghangting
 * @date 2018/10/21 20:53
 */
@WebService(endpointInterface="org.apache.servicemix.wsn.push.INotificationProcess",
        serviceName="INotificationProcess")
public class ShoppingArriveService implements INotificationProcess{
    // 分布在不同节点的服务，当前驱事件到达的时候，就会触发该服务
    @Override
    public void notificationProcess(String notification) {
        // 角色实例人员通过获取外部数据，完成该服务
        System.out.println(notification);
//        String[] dataSource = notification.split(";");
//        String name = dataSource[0];
//        String message = dataSource[1];
//        DeliveryStaff deliveryStaff = new DeliveryStaff(name);
//        deliveryStaff.doSomething(message);
    }
}
