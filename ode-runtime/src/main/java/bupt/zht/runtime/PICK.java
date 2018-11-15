package bupt.zht.runtime;

import bupt.zht.o.OPickReceive;
public class PICK extends ACTIVITY {
    private OPickReceive _opick;
    private OPickReceive.OnAlarm _alarm = null;

    public PICK(ActivityInfo self, ScopeFrame scopeFrame, LinkFrame linkFrame) {
        super(self, scopeFrame, linkFrame);
        _opick = (OPickReceive) self.o;
    }
    @Override
    public void run() {

    }
}
