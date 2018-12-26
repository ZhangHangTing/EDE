package bupt.zht.process;

import bupt.zht.EpcCompiler;
import bupt.zht.InstanceState;
/**
 * @author zhanghangting
 * @date 2018/12/15 20:58
 */
public class ProcessInstance {
    // processModelId标志该流程实例属于哪一个流程模型
    private String processModelId;
    // 通过instanceMark的标志来确定属于哪一个流程实例
    private String processInstanceId;
    // 一个流程实例中拥有一个属于每个实例的编译器，该编辑器中保存了流程实例的所有节点以及每个节点的状态
    private EpcCompiler epcCompiler;
    // 整个流程实例的状态
    private InstanceState instanceState;

    public
    ProcessInstance(String processModelId,String processInstanceId,EpcCompiler epcCompiler){
        this.processModelId = processModelId;
        this.processInstanceId = processInstanceId;
        this.epcCompiler = epcCompiler;
        this.instanceState = InstanceState.READY;
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

    public EpcCompiler getEpcCompiler() {
        return epcCompiler;
    }

    public void setEpcCompiler(EpcCompiler epcCompiler) {
        this.epcCompiler = epcCompiler;
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
    @Override
    public String toString(){
        return "{ 流程实例的ID是： " + processInstanceId  + " }";
    }
}
