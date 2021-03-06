package bupt.zht.bom.activity;

import bupt.zht.bom.Correlation;
import bupt.zht.bom.Correlations;
import org.dom4j.Element;

import java.util.Collections;
import java.util.List;

public class ReceiveActivity extends CreateInstanceActivity {
    public ReceiveActivity(Element el){
        super(el);
    }
    public String getVariable(){
        return getAttribute("variable");
    }
    public String getRoute(){
        return getAttribute("route");
    }
    public String getOperation(){
        return getAttribute("operation");
    }
    public String getPartnerLink(){
        return getAttribute("partnerLink");
    }
    public String getPortType(){
        return getAttribute("portType");
    }
    public List<Correlation> getCorrelations(){
        Correlations correlations = getFirstChild(Correlations.class);
        if(correlations == null)
            return Collections.emptyList();
        return null;
    }
}
