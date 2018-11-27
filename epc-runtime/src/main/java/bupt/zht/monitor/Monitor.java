package bupt.zht.monitor;

import bupt.zht.ProcessInfo;
import bupt.zht.pubsub.ReceiveEventService;
import bupt.zht.pubsub.UpdateLogicTree;
import wsn.wsnclient.command.SendWSNCommand;

import javax.xml.ws.Endpoint;

// 这应该是一个定时任务，
public class Monitor {
    // 用于监听发布订阅系统事件
    public static void start() {
        // 在update-logictree分支上进行开发，以后不管在mac还是在windows下
        // 只需要先fetch一下 merge本地的代码开发即可
        // 地址应该更改成可配置的
        String subWebAddr = "http://10.108.164.106:9016/wsn-subscribe";// 订阅用户webservice地址
        String wsnAddr = "http://10.108.164.106:9000/wsn-core";// 发布订阅节点地址

        ReceiveEventService receiveEventService = new ReceiveEventService();
        //subWebAddr表示一个服务的地址，发布订阅系统的结点会将消息发送到这个webservice地址上。然后impl表示处理消息的实现类，用于处理结点发送到这个地址的消息的逻辑
        Endpoint.publish(subWebAddr, receiveEventService);//更新逻辑事件表达树
        SendWSNCommand sendWSNCommand = new SendWSNCommand(subWebAddr, wsnAddr);
        // 这里需要订阅一个流程下事件发出的主题
        for (String eventTheme : ProcessInfo.eventThemeList) {
            try {
                String subRes = sendWSNCommand.subscribe(eventTheme);
                if (subRes.equals("ok")) {
                    System.out.println(eventTheme + " 主题订阅成功！");
                } else {
                    System.out.println(eventTheme + " 主题订阅失败！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
