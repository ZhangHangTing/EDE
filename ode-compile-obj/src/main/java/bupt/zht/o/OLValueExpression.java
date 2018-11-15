package bupt.zht.o;

public abstract class OLValueExpression extends OExpression {
    private static final long serialVersionUID = 1L;
    public OLValueExpression(OProcess owner){
        super(owner);
    }
    public abstract OScope.Variable getVariable();
}
