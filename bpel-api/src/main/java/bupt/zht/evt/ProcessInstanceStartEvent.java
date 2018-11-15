package bupt.zht.evt;

public class ProcessInstanceStartEvent extends ProcessInstanceEvent {
    private Long _rootScopeId;
    private int _scopeDeclarationId;
    public ProcessInstanceStartEvent(){
        super();
    }
    public Long getRootScopeId(){
        return _rootScopeId;
    }
    public int getScopeDeclarationId(){
        return _scopeDeclarationId;
    }
    public void setRootScopeId(Long _rootScopeId) {
        this._rootScopeId = _rootScopeId;
    }
    public void setScopeDeclarationId(int _scopeDeclarationId) {
        this._scopeDeclarationId = _scopeDeclarationId;
    }
}
