package bupt.zht.bom.activity;

import bupt.zht.bom.Activity;
import bupt.zht.bom.Expression;
import org.dom4j.Element;


public class WhileActivity extends Activity {
    public WhileActivity(Element el){
        super(el);
    }
    //获取子节点活动标签
    public Activity getActivity(){
        return getFirstChild(Activity.class);
    }
    //获取循环的条件
    public Expression getCondition(){
        return getFirstChild(Expression.class);
    }

}
