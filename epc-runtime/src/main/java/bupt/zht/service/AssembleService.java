package bupt.zht.service;
import engin.pubsub.Publisher;

import javax.jws.WebService;

@WebService(endpointInterface="org.apache.servicemix.wsn.push.INotificationProcess",
        serviceName="INotificationProcess")
public class AssembleService implements Service{
    private String tool;
    private String assembleSatff;
    public void setTool(String tool) {
        this.tool = tool;
    }
    public void setAssembleSatff(String assembleSatff) {
        this.assembleSatff = assembleSatff;
    }
    @Override
    public void run() {
        if(validate()){
            System.out.println("正在执行组装产品任务。。。。。");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(assembleSatff + " 执行完毕!");
            Publisher.publish(assembleSatff,"all:finishProduct","all:finishProduct;产品已经组装完成");
        }
    }

    @Override
    public boolean validate() {
        // 通过前端对于权限控制的操作，获取人员权限的信息进行验证
        return false;
    }
}
