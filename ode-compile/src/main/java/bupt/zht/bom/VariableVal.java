package bupt.zht.bom;


import org.dom4j.Element;

public class VariableVal extends BpelObject {
    public VariableVal(Element el){
        super(el);
    }
    public String getVariable(){
        return getAttribute("variable");
    }
    public String getPart(){
        return getAttribute("part");
    }
    public String getHeader(){
        return getAttribute("header");
    }
//    public Expression getLocation(){
//        return getFirstChild();
//    }
    public VariableVal getAsVariableVal(){
        if(getAttribute("variable") != null){
            return new VariableVal(getElement());
        }
        return null;
    }
}
