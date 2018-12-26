package bupt.zht.pubsub;

import bupt.zht.activity.Function;
import bupt.zht.service.Service;
import bupt.zht.service.ServiceFactory;

 /*
   * @author zhanghangting
   * @date 2018/11/27 16:52
   */
public class ExecuteFunction {
    public static void executeFunction(Function function, String message){
        System.out.println("执行函数的服务类名：" + function.getServiceName());
        Service executeService = ServiceFactory.getServiceInstance(function.getServiceName());
        executeService.run(message);
    }
}
