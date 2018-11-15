package bupt.zht.bom.activity;

import bupt.zht.bom.Activity;
import bupt.zht.bom.Copy;
import org.dom4j.Element;

import java.util.List;
public class AssignActivity extends Activity {
    public AssignActivity(Element el){
        super(el);
    }
    //得到assign标签
    // 活动的copy标签列表
    public List<Copy> getCopies(){
        return getChildren(Copy.class);
    }
}
