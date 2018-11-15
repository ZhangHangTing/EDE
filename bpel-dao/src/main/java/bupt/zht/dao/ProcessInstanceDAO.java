package bupt.zht.dao;

import java.util.Collection;
import java.util.Date;

public interface ProcessInstanceDAO {
    Date getCreateTime();
    Date getLastActiveTime();
    void setLastActiveTime();
    byte[] getExecutionState();
    void setExecutionState(byte[] executionState);
    ProcessDAO getProcess();
    ScopeDAO getRootScope();
    void setState(short state);
    short getState();
    short getPreviousState();
    ScopeDAO createScope(ScopeDAO parentScope,String name,int scopeModelId);
    Long getInstanceId();
    ScopeDAO getScope(Long scopeInstanceId);
    Collection<ScopeDAO> getScopes(String scopeName);
    Collection<ScopeDAO> getScopes();
}
