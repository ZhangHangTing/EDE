package bupt.zht.o;


import org.dom4j.Document;
import org.dom4j.Node;

public abstract class OVarType extends OBase {
    public OVarType(OProcess owner){
        super(owner);
    }
    public abstract Node newInstance(Document doc);
}
