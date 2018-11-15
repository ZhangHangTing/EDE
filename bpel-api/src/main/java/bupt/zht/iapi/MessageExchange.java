package bupt.zht.iapi;

import javax.wsdl.Operation;
import javax.wsdl.PortType;
import java.util.Set;

//BPEL引擎和外部链接伙伴的交流
public interface MessageExchange {
    enum MessageExchangePattern{
        REQUEST_ONLY,
        REQUEST_RESPONSE,
        UNKNOW
    }
    enum Status{
        NEW,
        REQUEST,
        ASYNC,
        RESPONSE,
        FAULT,
        FAILURE,
        COMPLETE_OK,
        COMPLETE_FAULT,
        COMPLETE_FAILURE
    }
    String getMessageExchangeId();
    String getOperationName();
    Status getStatus();
    Operation getOperation();
    PortType getPortType();
    void setProperty(String key,String value);
    String getProperty(String key);
    Set<String> getPropertyNames();
}
