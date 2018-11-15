package bupt.zht.bom;


import org.dom4j.Element;

public class Copy extends BpelObject {
    public Copy(Element el){
        super(el);
    }
    //得到左边的值
    public To getTo(){
        return getFirstChild(To.class);
    }
    //得到右边的值
    public From getFrom(){
        return getFirstChild(From.class);
    }
}
