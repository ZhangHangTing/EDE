package bupt.zht.bom.generator;

import bupt.zht.bom.Activity;
import bupt.zht.bom.Copy;
import bupt.zht.bom.activity.AssignActivity;
import bupt.zht.o.OActivity;
import bupt.zht.o.OAssign;

public class AssignGenerator extends DefaultActivityGenerator {
    public OActivity newInstance(Activity src){
        return new OAssign(_context.getOProcess(),_context.getCurrent());
    }
    //将一个源Activity对象编译成一个目的OActivity对象
    public void compile(OActivity dest, Activity source) {
        OAssign oassign = (OAssign) dest;
        AssignActivity ad = (AssignActivity) source;
        //在这里就将Assign下面的copy标签里面的所有元素都进行处理，包括from的取值和to的赋值
    }
}
