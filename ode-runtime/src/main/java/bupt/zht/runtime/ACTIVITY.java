package bupt.zht.runtime;

import bupt.zht.evt.ActivityEvent;
import bupt.zht.evt.EventContext;
import bupt.zht.evt.ScopeEvent;
import bupt.zht.evt.VariableReadEvent;
import bupt.zht.o.OActivity;
import bupt.zht.o.OLink;
import bupt.zht.o.OScope;
import org.apache.ode.jacob.IndexedObject;
import org.dom4j.Node;

import java.io.Serializable;
import java.util.Collection;

public abstract class ACTIVITY extends BpelJacobRunnable implements IndexedObject {
    protected ActivityInfo _self;
    protected ScopeFrame _scopeFrame;
    protected  LinkFrame _linkFrame;
    public ACTIVITY(ActivityInfo self,ScopeFrame scopeFrame,LinkFrame linkFrame){
        _self = self;
        _scopeFrame = scopeFrame;
        _linkFrame = linkFrame;
    }
    public Object getKey(){
        return new Key(_self.o,_self.aId);
    }
    protected void sendEvent(ActivityEvent event){
        event.setActivityName(_self.o.name);
        event.setActivityType(_self.o.getType());
        event.setActivityDeclarationId(_self.o.getId());
        event.setAid(_self.aId);
        if(event.getLineNo() == -1){
            event.setLineNo(getLineNo());
        }
        sendEvent((ScopeEvent)event);
    }
    protected void sendEvent(ScopeEvent event){
        if(event.getLineNo() == -1 && _self.o.debugInfo != null){
            event.setLineNo(_self.o.debugInfo.startLine);
        }
        _scopeFrame.fillEventInfo(event);
        fillEventContext(event);
        getBpelRuntimeContext().sendEvent(event);
    }
    protected void sendVariableReadEvent(VariableInstance var){
        VariableReadEvent vre = new VariableReadEvent();
        vre.setVarName(var.declaration.name);
        sendEvent(vre);
    }
    Node fetchVariableData(VariableInstance variable,boolean forWriting){
        return _scopeFrame.fetchVariableData(getBpelRuntimeContext(),variable,forWriting);

    }
    protected void fillEventContext(ScopeEvent event){
        EventContext eventContext = new EventContextImpl(_scopeFrame.oscope,_scopeFrame.scopeInstanceId,getBpelRuntimeContext());
        event.eventContext = eventContext;
    }
    private int getLineNo(){
        if(_self.o.debugInfo != null && _self.o.debugInfo.startLine != -1)
            return _self.o.debugInfo.startLine;
        return -1;
    }
    protected void dpe(OActivity activity){
        dpe(activity.sourceLinks);
        dpe(activity.outgoingLinks);
    }
    protected void dpe(Collection<OLink> links){
        for(OLink link : links){
            _linkFrame.resolve(link).pub.linkStatus(false);
        }
    }
    public static final class Key implements Serializable{
        final OActivity type;
        final long aid;
        public Key(OActivity type,long aid){
            this.type = type;
            this.aid = aid;
        }
    }
}
