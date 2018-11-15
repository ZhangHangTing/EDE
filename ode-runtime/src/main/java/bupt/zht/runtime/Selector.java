package bupt.zht.runtime;

import java.io.Serializable;

public class Selector implements Serializable {
    public final PartnerLinkInstance plinkInstance;
    public Object correlationKey = null;
    public final String opName;
    public final String messageExchangeId;
   // public CorrelationKeySet correlationKeySet;
    public final boolean oneWay;
    public final String route;
    public final int idx;
    public Selector(int idx, PartnerLinkInstance partnerLinkInstance,String opName,boolean oneWay, String mexId, String route){
        this.idx =idx;
        this.plinkInstance = partnerLinkInstance;
        this.correlationKey = correlationKey;
        this.opName = opName;
        this.messageExchangeId = mexId;
        this.oneWay = oneWay;
        this.route = route;
    }
}
