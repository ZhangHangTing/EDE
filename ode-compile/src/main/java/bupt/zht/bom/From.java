package bupt.zht.bom;


import org.dom4j.Element;

public class From extends BpelObject {
    public From(Element el){
        super(el);
    }
    public VariableVal getAsVariableVal(){
        if(getAttribute("variable") != null){
            return new VariableVal(getElement());
        }
        return null;
    }
    public Expression getExpression(){
        return new Expression(getElement());
    }
}
