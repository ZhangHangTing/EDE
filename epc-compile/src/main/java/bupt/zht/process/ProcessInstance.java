package bupt.zht.process;

import bupt.zht.InstanceState;
import bupt.zht.ProcessInfo;
import bupt.zht.activity.Function;
import java.util.List;
/**
 * @author zhanghangting
 * @date 2018/12/15 20:58
 */
public class ProcessInstance {

    // 通过instanceMark的标志来确定属于哪一个流程实例
    private String instanceMark;
    // processModelId标志该流程实例属于哪一个流程模型
    private String processModelId;
    // 流程实例Id唯一标志流程实例，该值通过计算instanceMark的hashcode值得到
    private String processInstanceId;
    private List<Function> processFunctionList;
    private InstanceState instanceState;

    public ProcessInstance(String processModelId,String instanceMark,List<Function> processFunctionList){
        this.instanceMark = instanceMark;
        this.processModelId = processModelId;
        this.processInstanceId = String.valueOf(instanceMark.hashCode());
        instanceState = InstanceState.READY;
        this.processFunctionList = processFunctionList;
    }

    public String getInstanceMark() {
        return instanceMark;
    }

    public void setInstanceMark(String instanceMark) {
        this.instanceMark = instanceMark;
    }

    public String getProcessModelId() {
        return processModelId;
    }

    public void setProcessModelId(String processModelId) {
        this.processModelId = processModelId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public List<Function> getProcessFunctionList() {
        return processFunctionList;
    }

    public void setProcessFunctionList(List<Function> processFunctionList) {
        this.processFunctionList = processFunctionList;
    }

    public InstanceState getInstanceState() {
        return instanceState;
    }

    public void setInstanceState(InstanceState instanceState) {
        this.instanceState = instanceState;
    }
    @Override
    public int hashCode(){
        return 1;
    }
    @Override
    public boolean equals(Object processInstance){
        if(processInstance instanceof ProcessInstance && ((ProcessInstance) processInstance).getProcessInstanceId().equals(this.processInstanceId)){
            return true;
        }
        return false;
    }
}
