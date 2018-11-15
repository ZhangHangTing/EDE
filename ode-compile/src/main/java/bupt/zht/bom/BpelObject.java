package bupt.zht.bom;


import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.QName;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class BpelObject {
    //element表示bpel对象表示的xml中的标签元素
    private Element _element;
    //标签元素中的属性
    private QName _type;
    //这个标签元素下面的直接子节点
    public List<BpelObject> _children = null;
    //bpel文件的统一资源定位符
    private URI _docURI;
    //基类对象的构造方法，所有的对象最终都会通过super的方法调用到基类的构造方法，会最先初始化基类的BpelObject对象
    public BpelObject(Element el){
        _element = el;
    }

    public Element getElement(){
        return _element;
    }
    public String getAttribute(String name){
        String val = _element.attribute(name).getValue();
        return val;
    }
    protected BpelObject createBpelObject(Element element) {
        return null;
    }
    protected <T extends BpelObject> T getFirstChild(Class<T> cls){
        List<T> children = getChildren(cls);
        if(children.size() == 0)
            return null;
        return children.get(0);
    }
//    protected List<BpelObject> getChildren(String type){
//        //return _children.get(0) ;
//        List<BpelObject> children = getChildren();
//        if(children.size() == 0)
//            return null;
//        return _children;
//    }
    protected <T extends BpelObject>  List<T> getChildren(Class<T> cls){
        //List<T> children = getChildren();
        return null;
    }
    protected BpelObject getFirstChild(String type){
        List<BpelObject> children = getChildren();
        if(children.size() == 0)
            return null;
        return children.get(0);
    }
    protected List<BpelObject> getChildren(){
        if(_children == null){
            _children = new ArrayList<BpelObject>();
             List<Node> nl = _element.elements();
            for(int i = 0;i < nl.size(); i++){
                Node node = nl.get(i);
                if(node.getNodeType() == Node.ELEMENT_NODE)
                    _children.add(createBpelObject((Element) node));
            }
        }
        return _children;
    }
    public URI getURI(){
        return _docURI;
    }

}
