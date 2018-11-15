package bupt.zht.bom.generator;

import bupt.zht.bom.Activity;
import bupt.zht.bom.activity.ReceiveActivity;
import bupt.zht.o.OActivity;
import bupt.zht.o.OPickReceive;

public class ReceiveGenerator extends DefaultActivityGenerator {
    public void compile(OActivity output, Activity src) {
        OPickReceive opick = (OPickReceive) output;
        ReceiveActivity recDef = (ReceiveActivity) src;
        //接下来对编译时候的OPickReceive进行设置
    }

    public OActivity newInstance(Activity src) {
        return new OPickReceive(_context.getOProcess(),_context.getCurrent());
    }
}
