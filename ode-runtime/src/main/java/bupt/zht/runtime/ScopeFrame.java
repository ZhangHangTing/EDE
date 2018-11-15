package bupt.zht.runtime;

import bupt.zht.evt.ScopeEvent;
import bupt.zht.o.OPartnerLink;
import bupt.zht.o.OScope;
import org.dom4j.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class ScopeFrame implements Serializable {
    private static final long serialVersionUID = 1l;
    final OScope oscope;
    final ScopeFrame parent;
    final Long scopeInstanceId;
    Set<CompensationHandler> availableCompensations;
    public ScopeFrame(OScope oScope,Long scopeInstanceId,ScopeFrame parent,Set<CompensationHandler> visibleCompensationHandlers) {
        this.oscope = oScope;
        this.scopeInstanceId = scopeInstanceId;
        this.parent = parent;
        this.availableCompensations = visibleCompensationHandlers;
    }
    public ScopeFrame find(OScope scope){
        if(oscope.name.equals(scope.name))
            return this;
        return (parent != null) ? parent.find(scope) : null;
    }
    public VariableInstance resolve(OScope.Variable variable){
        ScopeFrame scopeFrame = find(variable.declaringScope);
        if(scopeFrame == null)
            return null;
        return new VariableInstance(scopeFrame.scopeInstanceId,variable);
    }
    public PartnerLinkInstance resolve(OPartnerLink partnerLink){
        return new PartnerLinkInstance(find(partnerLink.declaringScope).scopeInstanceId,partnerLink);
    }
    public void fillEventInfo(ScopeEvent event){
        ScopeFrame currentScope = this;
        ArrayList<String> parentNames = new ArrayList<>();
        while(currentScope != null){
            parentNames.add(currentScope.oscope.name);
            currentScope = currentScope.parent;
        }
        event.setParentScopesNames(parentNames);
        if(parent != null){
            event.setParentScopeId(parent.scopeInstanceId);
        }
        event.setScopeId(scopeInstanceId);
        event.setScopeName(oscope.name);
        event.setScopeDeclarationId(oscope.getId());
        if(event.getLineNo() == -1 && oscope.debugInfo != null){
            event.setLineNo(oscope.debugInfo.startLine);
        }
    }
    public Node fetchVariableData(BpelRuntimeContext brc, VariableInstance variable, boolean forWriting) {
        Node data = brc.readVariable(variable.scopeInstance,variable.declaration.name,forWriting);
        if(data == null){

        }
        return data;
    }
}
