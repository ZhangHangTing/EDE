package bupt.zht.bom;


import org.dom4j.Element;

public class To extends BpelObject {
    public To(Element el){
        super(el);
    }
    public VariableVal getAsVariableVal(){
        if(getAttribute("variable") != null){
            return new VariableVal(getElement());
        }
        return null;
    }
}
