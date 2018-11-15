package bupt.zht.o;
import java.io.Serializable;
import java.util.*;
public class OScope extends OActivity {
    static final long serialVersionUID = -1L;
    public String name;
    public OActivity activity;
    //public OFaultHandler faultHandler;
    public final Map<String,CorrelationSet> correlationSets = new HashMap<String, CorrelationSet>();
    public final Map<String,OPartnerLink> partnerLinks = new HashMap<String,OPartnerLink>();
    public final Map<String,Variable> variables = new HashMap<String, Variable>();
    public final Set<OScope> compensate = new HashSet<OScope>();
    public OScope(OProcess owner,OActivity parent){
        super(owner,parent);
    }
    public CorrelationSet getCorrelationsSet(String corrName){
        return correlationSets.get(corrName);
    }
    public void addCorrelationSet(CorrelationSet ocset){
        correlationSets.put(ocset.name,ocset);
    }
    public Variable getLocalVariable(final String varName){
        return variables.get(varName);
    }

    public void addLocalVariable(Variable variable){
        variables.put(variable.name,variable);
    }
    public Variable getVisibleVariable(String varName){
        OActivity current = this;
        Variable variable;
        while(current != null) {
            if(current instanceof OScope){
                variable =((OScope)current).getLocalVariable(varName);
                if(variable != null){
                    return variable;
                }
            }
            current = current.getParent();
        }
        return null;
    }
    public OPartnerLink getLocalPartnerLink(String name){
        return partnerLinks.get(name);
    }
    public static final class CorrelationSet extends OBase{
        public  String name;
        public OScope declaringScope;
        public final List<OProcess.OProperty> properties = new ArrayList<OProcess.OProperty>();
        public boolean hasJoinUseCases = false;
        public CorrelationSet(OProcess owner){
            super(owner);
        }
    }
    public static final class Variable extends OBase{
        static final long serialVersionUID = -1L;
        public String name;
        public OScope declaringScope;
        public OVarType type;
        public Variable(OProcess owner,OVarType type){
            super(owner);
            this.type = type;
        }
        public String toString(){
            return "{Variable" + getDescription() + ":" +type + "}";
        }
        public String getDescription(){
            StringBuffer buf = new StringBuffer(declaringScope.name);
            buf.append('.');
            buf.append(name);
            return buf.toString();
        }
    }
}
