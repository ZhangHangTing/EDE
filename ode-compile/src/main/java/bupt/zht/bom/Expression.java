package bupt.zht.bom;


import org.dom4j.Element;
import org.dom4j.Node;

public class Expression extends BpelObject {
    public Expression(Element el){
        super(el);
    }
    public String getExpressionLanguage(){
        return  getAttribute("expressionLanguage");
    }
    public Node getExpression(){
//        getElement().normalize();
//        for(Node n = getElement().getFirstChild();n != null; n = n.getNextSibling()){
//            switch (n.getNodeType()){
//                case Node.TEXT_NODE:
//                    if(n.getNodeValue().trim().length() > 0)
//                        return n;
//                    else if(n.getNextSibling() != null)
//                        continue;
//                    else
//                        return n;
//                case Node.ELEMENT_NODE:
//                case Node.CDATA_SECTION_NODE:
//                    return n;
//            }
//        }
        return null;
    }
}
