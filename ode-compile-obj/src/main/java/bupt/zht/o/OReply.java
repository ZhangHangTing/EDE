package bupt.zht.o;

import org.dom4j.QName;

import javax.wsdl.Operation;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class OReply extends OActivity {
    static final long serialVersionUID = -1L;
    public boolean isFaultReply;
    public QName fault;
    public OPartnerLink partnerLink;
    public Operation operation;
    public OScope.Variable variable;
    public final List<OScope.CorrelationSet> initCorrelations = new ArrayList<OScope.CorrelationSet>();
    public final List<OScope.CorrelationSet> assertCorrelations = new ArrayList<OScope.CorrelationSet>();
    public final List<OScope.CorrelationSet> joinCorrelations = new ArrayList<OScope.CorrelationSet>();
    public String messageExchangeId = "";
    public OReply(OProcess owner,OActivity parent){
        super(owner,parent);
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if(joinCorrelations == null){
            try {
                Field field = OReply.class.getDeclaredField("joinCOrrealaions");
                field.setAccessible(true);
                field.set(this,new ArrayList<OScope.CorrelationSet>());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
