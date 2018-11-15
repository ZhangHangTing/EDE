package bupt.zht.bom;


import org.dom4j.Element;

public class Variable extends BpelObject{
    public Variable(Element el){
        super(el);
    }
    public String getName(){
        return getAttribute("name");
    }
}

