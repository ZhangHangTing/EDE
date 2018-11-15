package bupt.zht.o;

import javafx.beans.binding.ObjectExpression;

public class OLink extends OBase {
    static final long serialversionUID = -1L;
    public OFlow declaringFlow;
    public String name;
    public ObjectExpression transitionCondition;
    public OActivity source;
    public OActivity target;
    public OLink(OProcess owner){
        super(owner);
    }
}
