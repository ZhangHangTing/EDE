package bupt.zht.runtime;


import bupt.zht.evt.ProcessInstanceStartEvent;
import bupt.zht.o.OProcess;
import bupt.zht.o.OScope;
import bupt.zht.runtime.channels.FaultData;
import bupt.zht.runtime.channels.ParentScopeChannel;
import bupt.zht.runtime.channels.ParentScopeChannelListener;
import bupt.zht.runtime.channels.TerminationChannel;
import org.apache.ode.jacob.SynchChannel;
import org.dom4j.Element;

import java.util.Set;

public class PROCESS extends BpelJacobRunnable {
    private OProcess _oprocess;
    public PROCESS(OProcess process){
        _oprocess = process;
    }
    @Override
    public void run() {
        BpelRuntimeContext ntive =  getBpelRuntimeContext();
        Long scopeInstanceId = ntive.createScopeInstance(null,_oprocess.processScope);
        ProcessInstanceStartEvent evt = new ProcessInstanceStartEvent();
        evt.setRootScopeId(scopeInstanceId);
        evt.setScopeDeclarationId(_oprocess.processScope.getId());
        ntive.sendEvent(evt);
        ActivityInfo child = new ActivityInfo(genMonotonic(),_oprocess.processScope,
                newChannel(TerminationChannel.class),newChannel(ParentScopeChannel.class));
        ScopeFrame processFrame = new ScopeFrame(_oprocess.processScope,scopeInstanceId,null,null);
        instance(new SCOPE(child,processFrame,new LinkFrame(null)));

        object(new ParentScopeChannelListener(child.parent) {
            @Override
            public void compenstae(OScope scope, SynchChannel ret) {
            }
            //最主要的是完成方法
            @Override
            public void completed(FaultData fault, Set<CompensationHandler> compensations) {
                BpelRuntimeContext nativeAPI = (BpelRuntimeContext)getExtension(BpelRuntimeContext.class);
                if(fault == null){
                    nativeAPI.completeOk();
                }
                else{
                    nativeAPI.completeFault(fault);
                }
            }
            @Override
            public void cancelled() {
                this.completed(null,CompensationHandler.emptySet());
            }
            @Override
            public void failure(String reason, Element data) {
            }
        });
    }
}
