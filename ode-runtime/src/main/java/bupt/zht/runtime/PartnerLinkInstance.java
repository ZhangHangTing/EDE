package bupt.zht.runtime;

import bupt.zht.o.OPartnerLink;

import java.io.Serializable;

public class PartnerLinkInstance implements Serializable {
    public OPartnerLink partnerLink;
    public Long scopeInstanceId;
    public PartnerLinkInstance(Long scopeInstanceId,OPartnerLink partnerLink){
        this.partnerLink = partnerLink;
        this.scopeInstanceId = scopeInstanceId;
    }
    public boolean equeals(Object obj){
        PartnerLinkInstance other = (PartnerLinkInstance) obj;
        return partnerLink.euqals(other.partnerLink) && scopeInstanceId.equals(other.scopeInstanceId);
    }
    public int hashCode(){
        return this.partnerLink.hashCode()^ scopeInstanceId.hashCode();
    }
}
