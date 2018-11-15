package bupt.zht.bom;


import org.dom4j.Element;

public class LinkTarget extends BpelObject {
    public LinkTarget(Element el){
        super(el);
    }
    public  String getLinkName(){
        return getAttribute("linkName");
    }
}
