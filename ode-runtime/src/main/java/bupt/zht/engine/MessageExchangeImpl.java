package bupt.zht.engine;

import bupt.zht.dao.MessageExchangeDAO;
import bupt.zht.evt.BpelEvent;
import bupt.zht.iapi.MessageExchange;

import javax.wsdl.Operation;
import javax.wsdl.PortType;
import java.util.Set;

public class MessageExchangeImpl implements MessageExchange {
    protected Long _iid;
    protected PortType _portType;
    protected Operation _operation;
    protected final BpelEngineImpl _engine;
    protected MessageExchangeDAO _dao;
    public MessageExchangeImpl(BpelEngineImpl engine,MessageExchangeDAO dao){
        _engine = engine;
        _dao = dao;
    }
    @Override
    public String getMessageExchangeId() {
        return null;
    }

    @Override
    public String getOperationName() {
        return null;
    }

    @Override
    public Status getStatus() {
        return null;
    }

    @Override
    public Operation getOperation() {
        return null;
    }

    @Override
    public PortType getPortType() {
        return null;
    }

    @Override
    public void setProperty(String key, String value) {

    }

    @Override
    public String getProperty(String key) {
        return null;
    }

    @Override
    public Set<String> getPropertyNames() {
        return null;
    }
}
