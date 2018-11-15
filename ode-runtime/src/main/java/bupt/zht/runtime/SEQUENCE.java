package bupt.zht.runtime;

import bupt.zht.o.OActivity;
import bupt.zht.o.OScope;
import bupt.zht.o.OSequence;
import bupt.zht.runtime.channels.*;
import org.apache.ode.jacob.SynchChannel;
import org.dom4j.Element;

import java.util.*;

public class SEQUENCE extends ACTIVITY {
    private final List<OActivity> _remaining;
    private final Set<CompensationHandler> _compensations;
    public SEQUENCE(ActivityInfo self, ScopeFrame scopeFrame, LinkFrame linkFrame) {
        this(self, scopeFrame, linkFrame,((OSequence)self.o).sequence,CompensationHandler.emptySet());
    }
    public SEQUENCE(ActivityInfo self, ScopeFrame scopeFrame, LinkFrame linkFrame,List<OActivity> remaining,Set<CompensationHandler> compensations) {
        super(self, scopeFrame, linkFrame);
        _remaining = Collections.unmodifiableList(remaining);
        _compensations = Collections.unmodifiableSet(compensations);
    }
    @Override
    public void run() {
        final ActivityInfo child = new ActivityInfo(genMonotonic(),_remaining.get(0),newChannel(TerminationChannel.class),newChannel(ParentScopeChannel.class));
        instance(createChild(child,_scopeFrame,_linkFrame));
        instance(new ACTIVE(child));
    }
    private class ACTIVE extends BpelJacobRunnable{
        private ActivityInfo _child;
        private boolean _terminateRequested = false;
        ACTIVE(ActivityInfo child){
            _child = child;
        }
        @Override
        public void run() {
            object(false, new TerminationChannelListener(_self.self) {
                @Override
                public void terminate() {
                    replication(_child.self).terminate();
                    ArrayList<OActivity> remaining = new ArrayList<>(_remaining);
                    remaining.remove(0);
                    deadPathRemaining(remaining);
                    _terminateRequested = true;
                    instance(ACTIVE.this);
                }
            }.or(new ParentScopeChannelListener(_child.parent) {
                @Override
                public void compenstae(OScope scope, SynchChannel ret) {
                    _self.parent.compenstae(scope,ret);
                    instance(ACTIVE.this);
                }

                @Override
                public void completed(FaultData faultData, Set<CompensationHandler> compensations) {
                    TreeSet<CompensationHandler> comps = new TreeSet<>(_compensations);
                    comps.addAll(compensations);
                    if(faultData != null || _terminateRequested || _remaining.size() <= 1){
                        deadPathRemaining(_remaining);
                        _self.parent.completed(faultData,comps);
                    }
                    //如果没有发生错误，没有中断请求，孩子活动不为空
                    else{
                        ArrayList<OActivity> remaining = new ArrayList<>(_remaining);
                        remaining.remove(0);
                        instance(new SEQUENCE(_self,_scopeFrame,_linkFrame,remaining,comps));
                    }
                }
                @Override
                public void cancelled() {
                    completed(null,CompensationHandler.emptySet());
                }
                @Override
                public void failure(String reason, Element data) {
                    completed(null,CompensationHandler.emptySet());
                }
            }));
        }
        private void deadPathRemaining(List<OActivity> remaining){
            for(Iterator<OActivity> i = remaining.iterator(); i.hasNext();){
                dpe(i.next());
            }
        }
    }
}
