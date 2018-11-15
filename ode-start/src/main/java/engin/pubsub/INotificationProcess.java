package engin.pubsub;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * @author zhanghangting
 * @date 2018/10/21 18:36
 */
@WebService(targetNamespace = "http://org.apache.servicemix.wsn.push",name = "INotificationProcess")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface INotificationProcess {
    void notificationProcess(String notification);
}
