package bupt.zht.o;


import org.dom4j.QName;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OProcess extends OBase {
    public static int instanceCount = 0;
    static final long serialVersionUID = -1L;
    //每一个OProcess都有自己的一个guid，保证在整个运行流程中的全球唯一性
    public String guid;
    //public String uuid;
    public String processName;
    public OScope processScope;
    int _childIdCounter = 0;
    public final Set<OPartnerLink> allPartnerLinks = new HashSet<OPartnerLink>();
    public final HashSet<OExpressionLanguage> expressionLanguages = new HashSet<OExpressionLanguage>();
    public final List<OProperty> properties = new ArrayList<OProperty>();
    List<OBase> _children = new ArrayList<OBase>();
    public OProcess(){
        super(null);
        instanceCount++;
    }
    public OBase getChild(final int id){
        for(int i = _children.size() - 1; i >= 0; i--){
            OBase child = _children.get(i);
            if(child.getId() == id)
                return child;
        }
        return null;
    }
    public List<OBase> getChildren(){
        return _children;
    }
    public String getName(){
        return processName;
    }
    public OPartnerLink getPartnerLink(String name){
        for(OPartnerLink partnerLink : allPartnerLinks){
            if(partnerLink.getName().equals(name))
                return partnerLink;
        }
        return null;
    }
    public Set<OPartnerLink> getAllPartnerLinks(){
        return allPartnerLinks;
    }

    public static class OProperty extends OBase {

        static final long serialVersionUID = -1L  ;
        public final List<OPropertyAlias> aliases = new ArrayList<OPropertyAlias>();
        public QName name;

        public OProperty(OProcess process) { super(process); }

        public OPropertyAlias getAlias(OVarType messageType) {
            for (OPropertyAlias aliase : aliases)
                if (aliase.varType.equals(messageType))
                    return aliase;
            return null;
        }

        public String toString() {
            return "{OProperty " + name + "}";
        }
    }

    public static class OPropertyAlias extends OBase {

        static final long serialVersionUID = -1L  ;

        public OVarType varType;

        public OExpression location;

        public OPropertyAlias(OProcess owner) {super(owner); }

        public String toString() {
            return "{OPropertyAlias " + getDescription() +  "}";
        }

        public String getDescription() {
            StringBuffer buf = new StringBuffer(varType.toString());
            buf.append('[');
            if (location != null) {
                buf.append("][");
                buf.append(location.toString());
            }
            buf.append(']');
            return buf.toString();
        }
    }
}
