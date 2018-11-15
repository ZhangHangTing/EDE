package bupt.zht.runtime;

import bupt.zht.evt.ProcessInstanceEvent;
import bupt.zht.o.OPartnerLink;
import bupt.zht.o.OProcess;
import bupt.zht.o.OScope;
import bupt.zht.runtime.channels.FaultData;
import bupt.zht.runtime.channels.InvokeResponseChannel;
import bupt.zht.runtime.channels.PickResponseChannel;
import bupt.zht.runtime.channels.TimerResponseChannel;
import com.sun.xml.internal.ws.wsdl.writer.document.http.Operation;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.QName;

import java.net.URI;
import java.util.Collection;
import java.util.Date;

public interface BpelRuntimeContext {
    Long getPid();
    void sendEvent(ProcessInstanceEvent event);
    boolean isVariableInitialized(VariableInstance variable);
    Long createScopeInstance(Long parentScopeId,OScope scopeType);
    void initializePartnerLinks(Long parentScopeId, Collection<OPartnerLink> partnerLinks);
    Node readVariable(Long scopeInstanceId,String varname,boolean forWriting);
    void initializePartnersSessionId(PartnerLinkInstance pLink,String session);
    String readProperty(VariableInstance var, OProcess.OProperty property);
    void completeOk();
    void completeFault(FaultData faultData);
    void select(PickResponseChannel response, Date timeout, boolean createInstance, Selector[] selectord);
    void cancel(TimerResponseChannel timerResponseChannel);
    void reply(PartnerLinkInstance plink, String opName, String mexId, Element msg, QName fault);
    String invoke(int acticityId, PartnerLinkInstance partnerLinkInstance, Operation operation, Element outboundMsg,
                  InvokeResponseChannel invokeResponseChannel);
    void terminate();
    long genId();
    Element getPartnerResponse(String mexId);
    Element getMyRequest(String mexId);
    QName getPartnerResponseType(String mexId);
    Element getSourceERP(String mexId);
    void recoverActivity(String channel, long activityId, String action, FaultData fault);
    void getSourceSessionId(String mexId);
    URI getBaseResourceURI();
    Node getProcessProperty(QName propertyName);
    QName getProcessQName();
}
