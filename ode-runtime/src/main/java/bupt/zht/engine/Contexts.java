package bupt.zht.engine;

import bupt.zht.evt.BpelEvent;
import bupt.zht.iapi.BpelEventListener;
import bupt.zht.iapi.Scheduler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Contexts {
    public Scheduler scheduler;
    final List<BpelEventListener> eventListeners = new CopyOnWriteArrayList<>();

}
