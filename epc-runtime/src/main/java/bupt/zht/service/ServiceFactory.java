package bupt.zht.service;


public class ServiceFactory {
    // 利用反射和工厂模式创建服务对象
    public static Service getServiceInstance(String serviceName,String data,String staff){
        try {
            // serviceName表示类的名称
            return (Service)Class.forName(serviceName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // 当流程引擎测试通过以后，将其改成利用发射创建对象，这样只需要通过修改配置文件，来创建对象，无需修改工厂代码
}
