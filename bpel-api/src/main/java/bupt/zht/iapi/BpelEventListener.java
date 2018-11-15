package bupt.zht.iapi;

import bupt.zht.evt.BpelEvent;

import java.util.Properties;

public interface BpelEventListener {
    void onEvent(BpelEvent bpelEvent);
    void startup(Properties configureProperties);
    void shutdown();
}
