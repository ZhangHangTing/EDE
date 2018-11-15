package bupt.zht.runtime;

import bupt.zht.evt.ActivityEnabledEvent;
import bupt.zht.o.OActivity;
import bupt.zht.o.OLink;

import java.util.HashMap;
import java.util.Map;

public class ACTIVITYGUARD extends ACTIVITY {
    private static final long serialVersionUID = 1l;
    private OActivity _oactivity;
    private boolean _firstTime = true;
    private Map<OLink,Boolean> _linkVals = new HashMap<>();
    public ACTIVITYGUARD(ActivityInfo self,ScopeFrame scopeFrame,LinkFrame linkFrame){
        super(self,scopeFrame,linkFrame);
        _oactivity = self.o;
    }
    @Override
    public void run() {
        //发送一个activity被激活的通知
        if(_firstTime){
            sendEvent(new ActivityEnabledEvent());
            _firstTime = false;
        }
        if(_linkVals.keySet().contains(_oactivity.targetLinks)){

        }
        //不知道所有我们连接的状态
        else{

        }

    }
}
