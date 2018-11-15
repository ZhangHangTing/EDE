package bupt.zht.runtime;

import bupt.zht.o.OActivity;
import bupt.zht.runtime.channels.ParentScopeChannel;
import bupt.zht.runtime.channels.TerminationChannel;

import java.io.Serializable;

public class ActivityInfo implements Serializable {
    private static final long serivalVersionUID = -1l;
    long aId;
    OActivity o;
    TerminationChannel self;
    ParentScopeChannel parent;
    ActivityInfo(long aid,OActivity o,TerminationChannel self,ParentScopeChannel parent){
        this.o = o;
        this.self = self;
        this.parent = parent;
        this.aId = aid;
    }
    public int hashCode(){
        return (int)aId;
    }
}
