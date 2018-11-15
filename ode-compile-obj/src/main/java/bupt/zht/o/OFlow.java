package bupt.zht.o;

import java.util.HashSet;
import java.util.Set;

public class OFlow extends OActivity {
    static final long serialVersionUID = -1L;
    public final Set<OLink> localLinks = new HashSet<OLink>();
    public final Set<OActivity> parallerActivities = new HashSet<OActivity>();
    public OFlow(OProcess owner,OActivity parent){
        super(owner,parent);
    }
    public OLink getLocalLink(final String linkName){
        return null;
    }
}
