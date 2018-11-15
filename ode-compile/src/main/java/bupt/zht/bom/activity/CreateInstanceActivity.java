package bupt.zht.bom.activity;

import bupt.zht.bom.Activity;
import org.dom4j.Element;

public class CreateInstanceActivity extends Activity{
    public CreateInstanceActivity(Element el){
        super(el);
    }
    public boolean isCreateInstance(){
        return getAttribute("createInstance").equals("yes");
    }
}
