package bupt.zht.service;

public class ServiceFactory {
    // 这里没有用到反射，每次增加一个Service，必须对改工厂类进行修改，耦合有问题
    public static Service getServiceInstance(String serviceName,String data,String staff){
        if("AssembleService".equalsIgnoreCase(serviceName)){
            return new AssembleService(data,staff);
        }else if("ProductAService".equalsIgnoreCase(serviceName)){
            return  new ProductAService(data,staff);
        }else{
            return null;
        }
    }
    // 当流程引擎测试通过以后，将其改成利用发射创建对象，这样只需要通过修改配置文件，来创建对象，无需修改工厂代码
}
