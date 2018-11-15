package bupt.zht.runtime;

import bupt.zht.o.OScope;

import java.io.Serializable;

public class VariableInstance implements Serializable {
    private static final long serialVersionUID = 1l;
    public final OScope.Variable declaration;
    public final Long scopeInstance;
    VariableInstance(Long scopeInstance,OScope.Variable variable){
        this.scopeInstance = scopeInstance;
        this.declaration = variable;
    }
    public boolean equals(Object obj){
        VariableInstance other = (VariableInstance) obj;
        return other.declaration.equals(declaration) && other.scopeInstance.equals(scopeInstance);
    }
}
