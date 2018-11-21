package engin.pubsub;

import engin.service.ShoppingArriveService;
import wsn.wsnclient.command.SendWSNCommand;
import javax.xml.ws.Endpoint;

/**
 * @author zhanghangting
 * @date 2018/10/21 18:35
 */
public class Subscriber {
//    static String subWebAddr = "http://10.108.164.106:9016/wsn-subscribe";// 订阅用户webservice地址
//    static String wsnAddr = "http://10.108.164.106:9000/wsn-core";// 发布订阅节点地址

//    public static void main(String[] args) {
//        ShoppingArriveService shoppingSolveService = new ShoppingArriveService();
//        Endpoint.publish(subWebAddr, shoppingSolveService);
//    }

    public static void main(String[] args) {
        String subWebAddr = "http://10.108.164.106:9016/wsn-subscribe";// 订阅用户webservice地址
        String wsnAddr = "http://10.108.164.106:9000/wsn-core";// 发布订阅节点地址
        //NotificationProcessImpl impl = new NotificationProcessImpl();
        ShoppingArriveService shoppingSolveService = new ShoppingArriveService();

        //subWebAddr表示一个服务的地址，发布订阅系统的结点会将消息发送到这个webservice地址上。然后impl表示处理消息的实现类，用于处理结点发送到这个地址的消息的逻辑
        Endpoint.publish(subWebAddr, shoppingSolveService);//开启接受服务

        SendWSNCommand sendWSNCommand = new SendWSNCommand(subWebAddr, wsnAddr);
        // 这里需要订阅所有事件发出的主题
        String topic = "all:shoppingarrive";

        try {
//			for(int i=0;i<topics.length;i++){
            String subResult = sendWSNCommand.subscribe(topic);
            if(subResult.equals("ok")){
                System.out.println(topic+"主题订阅成功！！！！！");
            } else
                System.err.println(topic+"主题订阅失败！！！！");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
