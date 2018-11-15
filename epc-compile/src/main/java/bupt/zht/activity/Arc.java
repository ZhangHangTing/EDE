package bupt.zht.activity;

import bupt.zht.EpcObject;

/**
 * @author zhanghangting
 * @date 2018/10/14 15:50
 */
public class Arc extends EpcObject{

    private Flow flow;

    public Arc(String id, String name) {
        super(id, name);
    }

    public Flow getFlow() {
        return flow;
    }

    public void setFlow(Flow flow) {
        this.flow = flow;
    }
}
