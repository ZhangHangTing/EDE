package bupt.zht.engine;

import bupt.zht.dao.MessageExchangeDAO;
import bupt.zht.iapi.Message;
import bupt.zht.iapi.MyRoleMessageExchange;
import org.dom4j.QName;

import java.util.concurrent.Future;

public class MyRoleMessageExchangeImpl extends MessageExchangeImpl implements MyRoleMessageExchange {
    public MyRoleMessageExchangeImpl(BpelEngineImpl engine, MessageExchangeDAO dao) {
        super(engine, dao);
    }

    @Override
    public CorrelationStatus getCorrelationStatus() {
        return null;
    }

    @Override
    public Future invoke(Message request) {
        return null;
    }

    @Override
    public void complete() {

    }

    @Override
    public void setClientId(String clientKey) {

    }

    @Override
    public String getClientId() {
        return null;
    }

    @Override
    public QName getServiceName() {
        return null;
    }

    @Override
    public void release(boolean instanceSucceeded) {

    }
}
