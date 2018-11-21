package engin.pubsub;

import wsn.wsnclient.command.SendWSNCommandWSSyn;

/**
 * @author zhanghangting
 * @date 2018/10/23 14:42
 */
public class Publisher {

    private static  String webserviceAddr = "http://10.108.164.106:9002/wsn-core-subscriber";
    private static  String wsnAddr = "http://10.108.164.106:9000/wsn-core";
    public static final  SendWSNCommandWSSyn command = new SendWSNCommandWSSyn(webserviceAddr, wsnAddr);
    public static void main(String[] args){
        Publisher.publish("kobe","all:shoppingarrive","货物已经到达");
    }

    public static void publish(String publisher, String topic, String message) {
        while(true){
            //发布者发布主题为all:Test2的message消息
            command.reliableNotify(topic,message, false, "A");
            System.out.println(publisher + "已经发布消息成功!");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
