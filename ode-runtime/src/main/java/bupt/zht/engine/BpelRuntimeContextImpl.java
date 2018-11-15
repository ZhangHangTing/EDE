package bupt.zht.engine;

import bupt.zht.dao.ProcessInstanceDAO;
import bupt.zht.evt.ProcessInstanceEvent;
import bupt.zht.o.OPartnerLink;
import bupt.zht.o.OProcess;
import bupt.zht.o.OScope;
import bupt.zht.runtime.*;
import bupt.zht.runtime.channels.FaultData;
import bupt.zht.runtime.channels.InvokeResponseChannel;
import bupt.zht.runtime.channels.PickResponseChannel;
import bupt.zht.runtime.channels.TimerResponseChannel;
import com.sun.xml.internal.ws.wsdl.writer.document.http.Operation;
import org.apache.ode.jacob.vpu.ExecutionQueueImpl;
import org.apache.ode.jacob.vpu.JacobVPU;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.QName;

import java.net.URI;
import java.util.Collection;
import java.util.Date;

public class BpelRuntimeContextImpl implements BpelRuntimeContext {
    protected ProcessInstanceDAO _dao;
    protected JacobVPU _vpu;
    protected ExecutionQueueImpl _soup;
    protected BpelProcess _bpelProcess;
    public BpelRuntimeContextImpl(BpelProcess bpelProcess, ProcessInstanceDAO processInstanceDAO,
                                  PROCESS process,MyRoleMessageExchangeImpl myRoleMessageExchange) {
        _bpelProcess = bpelProcess;
        _dao = processInstanceDAO;
        _vpu = new JacobVPU();
        _soup = new ExecutionQueueImpl(null);
        _vpu.setContext(_soup);
        //获取流程实例的两种方式，一种的在内存中暂时性，一种是序列化的
        //ProcessInstanceDAOImpl inmem = (ProcessInstanceDAOImpl) _dao;
        //将流程注入
        if(process != null){
            _vpu.inject(process);
        }
    }

    @Override
    public Long getPid() {
        return null;
    }

    @Override
    public void sendEvent(ProcessInstanceEvent event) {

    }

    @Override
    public boolean isVariableInitialized(VariableInstance variable) {
        return false;
    }

    @Override
    public Long createScopeInstance(Long parentScopeId, OScope scopeType) {
        return null;
    }

    @Override
    public void initializePartnerLinks(Long parentScopeId, Collection<OPartnerLink> partnerLinks) {

    }

    @Override
    public Node readVariable(Long scopeInstanceId, String varname, boolean forWriting) {
        return null;
    }

    @Override
    public void initializePartnersSessionId(PartnerLinkInstance pLink, String session) {

    }

    @Override
    public String readProperty(VariableInstance var, OProcess.OProperty property) {
        return null;
    }

    @Override
    public void completeOk() {

    }

    @Override
    public void completeFault(FaultData faultData) {

    }

    @Override
    public void select(PickResponseChannel response, Date timeout, boolean createInstance, Selector[] selectord) {

    }

    @Override
    public void cancel(TimerResponseChannel timerResponseChannel) {

    }

    @Override
    public void reply(PartnerLinkInstance plink, String opName, String mexId, Element msg, QName fault) {

    }

    @Override
    public String invoke(int acticityId, PartnerLinkInstance partnerLinkInstance, Operation operation, Element outboundMsg, InvokeResponseChannel invokeResponseChannel) {
        return null;
    }

    @Override
    public void terminate() {

    }

    @Override
    public long genId() {
        return 0;
    }

    @Override
    public Element getPartnerResponse(String mexId) {
        return null;
    }

    @Override
    public Element getMyRequest(String mexId) {
        return null;
    }

    @Override
    public QName getPartnerResponseType(String mexId) {
        return null;
    }

    @Override
    public Element getSourceERP(String mexId) {
        return null;
    }

    @Override
    public void recoverActivity(String channel, long activityId, String action, FaultData fault) {

    }

    @Override
    public void getSourceSessionId(String mexId) {

    }

    @Override
    public URI getBaseResourceURI() {
        return null;
    }

    @Override
    public Node getProcessProperty(QName propertyName) {
        return null;
    }

    @Override
    public QName getProcessQName() {
        return null;
    }
}
