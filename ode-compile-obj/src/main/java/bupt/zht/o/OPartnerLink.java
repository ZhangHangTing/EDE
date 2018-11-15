package bupt.zht.o;

import com.sun.xml.internal.ws.wsdl.writer.document.PortType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class OPartnerLink extends OBase{
    static final long serialVersionUID = -1L;
    public String name;
    public OScope declaringScope;
    public String partnerLinkType;
    public String partnerRoleName;
    public String myRolePortType;
    public final Map<String,OPartnerLink> partnerLinks = new HashMap<String,OPartnerLink>();
    //wsdl引用
    //PortType
    private final HashSet<String> _createInstanceOperations = new HashSet<String>();
    public OPartnerLink(OProcess owner){
        super(owner);
    }
    public String getName(){
        return name;
    }
}
