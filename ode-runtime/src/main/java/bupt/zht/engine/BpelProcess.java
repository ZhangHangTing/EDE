package bupt.zht.engine;

import bupt.zht.dao.ProcessInstanceDAO;
import bupt.zht.iapi.ProcessConf;
import bupt.zht.iapi.ProcessState;
import bupt.zht.o.OProcess;
import bupt.zht.runtime.PROCESS;
import org.dom4j.QName;
import java.net.URI;

public class BpelProcess {
    private volatile OProcess _oprocess;
    final ProcessConf _pconf;
    final QName _pid;
    private String Qname;
    BpelEngineImpl _engine;
    public BpelProcess(ProcessConf conf){
        _pconf = conf;
        _pid = conf.getProcessId();
    }
    public URI getBaseResourceURI(){
        return this._pconf.getBaseURI();
    }
    protected boolean isActive(){
        return _pconf.getState() == ProcessState.ACTIVE;
    }

    public QName getPid() {
        return _pid;
    }
    public OProcess getOProcess(){
        return _oprocess;
    }
    public BpelEngineImpl getEngine(){
        return _engine;
    }
    QName getProcessType(){
        return _pconf.getType();
    }
    protected BpelRuntimeContextImpl createRuntimeContext(ProcessInstanceDAO dao, PROCESS template,
                                                          MyRoleMessageExchangeImpl instantiatingMessageExchange){
        return new BpelRuntimeContextImpl(this,dao,template,instantiatingMessageExchange);
    }
}
