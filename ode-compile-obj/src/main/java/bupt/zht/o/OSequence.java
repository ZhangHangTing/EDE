package bupt.zht.o;

import java.util.ArrayList;
import java.util.List;

public class OSequence extends OActivity {
    static final long serialVersionUID = -1L;
    public final List<OActivity> sequence = new ArrayList<OActivity>();
    public OSequence(OProcess owner, OActivity parent) {
        super(owner, parent);
    }
}
