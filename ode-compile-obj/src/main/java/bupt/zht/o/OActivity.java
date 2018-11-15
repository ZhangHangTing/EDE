package bupt.zht.o;

import java.util.HashSet;
import java.util.Set;

public class OActivity extends OBase{
     static final long serialVersionUID = -1L;
     public final Set<OLink> incomingLinks = new HashSet<OLink>();
     public final Set<OLink> outgoingLinks = new HashSet<OLink>();
     public final Set<OLink> sourceLinks = new HashSet<OLink>();
     public final Set<OLink> targetLinks = new HashSet<OLink>();
     public final Set<OScope.Variable> variableRd = new HashSet<OScope.Variable>();
     public final Set<OScope.Variable> variableWr = new HashSet<OScope.Variable>();
     public String name;
     private OActivity parent;
     public String getType(){
        return getClass().getSimpleName();
    }
     public OActivity(OProcess owner, OActivity parent) {
         super(owner);
         this.parent = parent;
     }
     public OActivity getParent(){
        return this.parent;
    }
}
