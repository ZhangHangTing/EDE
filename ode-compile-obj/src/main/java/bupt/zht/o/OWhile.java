package bupt.zht.o;

public class OWhile extends OActivity {
    static final long serialVersionUID = -1L;
    public OExpression whileCondition;
    public OActivity activity;
    public OWhile(OProcess owner,OActivity parent){
        super(owner,parent);
    }
}
