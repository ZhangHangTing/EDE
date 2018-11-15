package bupt.zht.o;

import java.util.ArrayList;
import java.util.List;

public class OPickReceive extends OActivity {
    static final long serialVersionUID = -1L;
    public final List<OnMessage> onMessages = new ArrayList<OnMessage>();
    public final List<OnAlarm> onAlarms = new ArrayList<OnAlarm>();
    public OPickReceive(OProcess owner, OActivity parent) {
        super(owner, parent);
    }
    public static class OnAlarm extends OBase{
        static final long serialVersionUID = -1L;
        public OnAlarm(OProcess owner) {
            super(owner);
        }
    }
    public static class OnMessage extends OBase{
        static final long serialVersionUID = -1L;
        public OnMessage(OProcess owner) {
            super(owner);
        }
    }
}
