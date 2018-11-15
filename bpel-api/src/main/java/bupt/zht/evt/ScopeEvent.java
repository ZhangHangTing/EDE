package bupt.zht.evt;

import java.util.List;

public abstract class ScopeEvent extends ProcessInstanceEvent {
    private Long _scopeId;
    private Long _parentScopeId;
    private String _scopeName;
    private int _scopeDeclarationId;
    private List<String> parentScopesNames;
    public ScopeEvent(){
        super();
    }
    public Long getScopeId() {
        return _scopeId;
    }
    public void setScopeId(Long _scopeId) {
        this._scopeId = _scopeId;
    }
    public Long getParentScopeId() {
        return _parentScopeId;
    }
    public void setParentScopeId(Long _parentScopeId) {
        this._parentScopeId = _parentScopeId;
    }

    public String getScopeName() {
        return _scopeName;
    }
    public void setScopeName(String _scopeName) {
        this._scopeName = _scopeName;
    }
    public int getScopeDeclarationId() {
        return _scopeDeclarationId;
    }
    public void setScopeDeclarationId(int _scopeDeclarationId) {
        this._scopeDeclarationId = _scopeDeclarationId;
    }

    public void setParentScopesNames(List<String> parentScopesNames) {
        this.parentScopesNames = parentScopesNames;
    }
    public List<String> getParentScopesNames() {
        return parentScopesNames;
    }
    public TYPE getTYPE(){
        return TYPE.scopeHandling;
    }
}
