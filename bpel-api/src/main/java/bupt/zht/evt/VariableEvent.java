package bupt.zht.evt;

public abstract class VariableEvent extends ScopeEvent{
    private String _varName;
    public VariableEvent(){
        super();
    }
    public  VariableEvent(String varName){
        super();
        _varName = varName;
    }
    public String getVarName(){
        return _varName;
    }
    public void setVarName(String varName){
        _varName = varName;
    }
    public TYPE getType(){
        return TYPE.dataHandling;
    }
}
