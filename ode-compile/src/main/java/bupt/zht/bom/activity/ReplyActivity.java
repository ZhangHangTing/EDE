package bupt.zht.bom.activity;

import bupt.zht.bom.Activity;
import bupt.zht.bom.Correlation;
import bupt.zht.bom.Correlations;
import org.dom4j.Element;

import java.util.Collections;
import java.util.List;

public class ReplyActivity extends Activity {
    public ReplyActivity(Element el){
        super(el);
    }
    public String getMessageExchangeId(){
        return getAttribute("messageExchange");
    }
    public String getVariable(){
        return getAttribute("variable");
    }
    public String getOperation(){
        return getAttribute("operation");
    }
    public String getPartnerLink(){
        return getAttribute("partnerlink");
    }
    public List<Correlation> getCorrelations(){
        Correlations correlations = getFirstChild(Correlations.class);
        if(correlations == null){
            return Collections.emptyList();
        }
        return null;
    }
}
