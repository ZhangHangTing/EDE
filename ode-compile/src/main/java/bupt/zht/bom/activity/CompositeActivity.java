package bupt.zht.bom.activity;

import bupt.zht.bom.Activity;
import org.dom4j.Element;

import java.util.List;

//BPEL组价活动标签中<flow>和<sequence>等标签的基本接口，这个接口提供了有序操作节点子活动的方法
public class CompositeActivity extends Activity {
    public CompositeActivity(Element el){
        super(el);
    }
    public List<Activity> getActivitys(){
        return getChildren(Activity.class);
    }
}
