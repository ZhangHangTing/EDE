package bupt.zht.service;

public class ServiceFactory {
    // 利用反射和工厂模式创建服务对象
    public static Service getServiceInstance(String serviceName){
        try {
            // serviceName表示类的名称
            return (Service)Class.forName(serviceName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
