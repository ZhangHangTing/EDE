package bupt.zht.activity;

import bupt.zht.EpcObject;

/**
 * @author zhanghangting
 * @date 2018/10/15 9:27
 */
public class Ou extends EpcObject{
    private String organizationUnit;
    private Flow flow;

    public Ou(String id, String name) {
        super(id, name);
    }

    public String getOrganizationUnit() {
        return organizationUnit;
    }
    public void setOrganizationUnit(String organizationUnit) {
        this.organizationUnit = organizationUnit;
    }
    public Flow getFlow() {
        return flow;
    }
    public void setFlow(Flow flow) {
        this.flow = flow;
    }
}
