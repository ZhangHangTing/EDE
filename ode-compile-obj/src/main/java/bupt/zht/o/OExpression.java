package bupt.zht.o;

import java.util.HashSet;

public class OExpression extends OBase{
    static final long serialVersionUID = -1L;
    public OExpressionLanguage expressionLanguage;
    public OExpression(OProcess owner){
        super(owner);
    }
    public OExpressionLanguage getExpressionLanguage(){
        return expressionLanguage;
    }

}
