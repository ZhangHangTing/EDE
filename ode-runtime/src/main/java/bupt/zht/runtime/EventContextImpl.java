package bupt.zht.runtime;

import bupt.zht.evt.EventContext;
import bupt.zht.o.OScope;

public class EventContextImpl implements EventContext {
    private OScope _scope;
    private Long _scopeInstanceId;
    private BpelRuntimeContext _runtimeContext;
    public EventContextImpl(OScope _scope,Long _scopeInstanceId,BpelRuntimeContext _runtimeContext){
        this._scope = _scope;
        this._scopeInstanceId = _scopeInstanceId;
        this._runtimeContext = _runtimeContext;
    }
    @Override
    public Long getScopeInstanceId() {
        return _scopeInstanceId;
    }
//这边需要解析
    @Override
    public String[] getVariableNames() {
        return new String[0];
    }

    @Override
    public String getVariableData(String varName) {
        return null;
    }
}
