package bupt.zht.bom.generator;

import bupt.zht.bom.Activity;
import bupt.zht.o.OActivity;
import bupt.zht.o.OReply;

public class ReplyGenerator extends DefaultActivityGenerator {
    public void compile(OActivity output, Activity src) {

    }

    public OActivity newInstance(Activity src) {
        return new OReply(_context.getOProcess(),_context.getCurrent());
    }

}
