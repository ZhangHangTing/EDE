package bupt.zht.service;

import engin.pubsub.Publisher;

import javax.jws.WebService;

@WebService(endpointInterface="org.apache.servicemix.wsn.push.INotificationProcess",
        serviceName="INotificationProcess")
public class ProductAService implements Service {
     private String material;
     private String productSatff;
    public void setMaterial(String material) {
        this.material = material;
    }
    public void setProductSatff(String productSatff) {
        this.productSatff = productSatff;
    }
    @Override
    public void run() {
        if(validate()){
            System.out.println("正在执行生成产品A任务。。。。。");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 首先执行自己的任务
            System.out.print(productSatff + " 执行完毕!");
            // 然后触发下一个事件
            Publisher.publish(productSatff,"all:finishProductA","all:finishProductA;产品A已经生成完成");
        }
    }

    @Override
    public boolean validate() {
        // 通过前端对于权限控制的操作，获取人员权限的信息进行验证
        return false;
    }
}
