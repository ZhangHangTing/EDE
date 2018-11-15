package bupt.zht.activity;

import bupt.zht.EpcObject;

/**
 * @author zhanghangting
 * @date 2018/10/14 15:48
 */
public class Function extends EpcObject{

    private String serviceName;
    private Flow flow;
    private Ou ou;
    private Iu iu;

    public Function(String id, String name) {
        super(id, name);
    }

    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Flow getFlow() {
        return flow;
    }

    public void setFlow(Flow flow) {
        this.flow = flow;
    }

    public Ou getOu() {
        return ou;
    }

    public void setOu(Ou ou) {
        this.ou = ou;
    }

    public Iu getIu() {
        return iu;
    }

    public void setIu(Iu iu) {
        this.iu = iu;
    }
}
