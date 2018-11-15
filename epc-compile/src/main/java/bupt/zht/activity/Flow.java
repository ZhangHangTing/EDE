package bupt.zht.activity;

import bupt.zht.EpcObject;

/**
 * @author zhanghangting
 * @date 2018/10/15 9:20
 */
public class Flow extends EpcObject{

    private String source;
    private String target;
    public Flow(String source,String target){
        this.source = source;
        this.target = target;
    }

    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
