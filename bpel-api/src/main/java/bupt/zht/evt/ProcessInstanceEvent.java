package bupt.zht.evt;

public class ProcessInstanceEvent extends ProcessEvent {
    private Long _pid;
    public ProcessInstanceEvent(){
        super();
    }
    public ProcessInstanceEvent(Long processInstanceId){
        _pid = processInstanceId;
    }
    public Long getProcessInstance(){
        return _pid;
    }
    public void setProcessInstanceId(Long pid){
        _pid = pid;
    }
    public TYPE getTypr(){
        return TYPE.instanceLifecycle;
    }
}
