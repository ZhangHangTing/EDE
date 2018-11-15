package bupt.zht.dao;

import bupt.zht.evt.BpelEvent;

import java.util.List;

public interface ScopeDAO {
    Long getScopeInstanceId();
    ScopeDAO getParentScope();
    List<BpelEvent> listEvents();
}
