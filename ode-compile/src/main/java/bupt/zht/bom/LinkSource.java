package bupt.zht.bom;


import org.dom4j.Element;

public class LinkSource extends BpelObject {
    public  LinkSource(Element el){
        super(el);
    }
    public String getLinkName(){
        return getAttribute("linkName");
    }
    public Expression getTransitionCondition(){
        return getFirstChild(Expression.class);
    }
}
