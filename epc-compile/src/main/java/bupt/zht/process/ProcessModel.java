package bupt.zht.process;

import bupt.zht.EpcCompiler;
import java.util.ArrayList;
import java.util.List;
/**
 * @author zhanghangting
 * @date 2018/12/15 20:58
 */
public class ProcessModel {

    // 通过processModelId这个标志来确定属于哪一个流程模型
    private String processModelId;
    private List<ProcessInstance> processInstancesList;
    private EpcCompiler epcCompiler;

    public ProcessModel(String processModelId,EpcCompiler epcCompiler){
        this.processModelId = processModelId;
        this.epcCompiler = epcCompiler;
        processInstancesList = new ArrayList<>();
    }

    public String getProcessModelId() {
        return processModelId;
    }

    public void setProcessModelId(String processModelId) {
        this.processModelId = processModelId;
    }

    public List<ProcessInstance> getProcessInstancesList() {
        return processInstancesList;
    }

    public void setProcessModelInstancesList(List<ProcessInstance> processModelInstancesList) {
        this.processInstancesList = processModelInstancesList;
    }

    public EpcCompiler getEpcCompiler() {
        return epcCompiler;
    }

    public void setEpcCompiler(EpcCompiler epcCompiler) {
        this.epcCompiler = epcCompiler;
    }

    @Override
    public int hashCode(){
        return 1;
    }
    @Override
    public boolean equals(Object processModel){
        if(processModel instanceof ProcessModel && ((ProcessModel) processModel).getProcessModelId().equals(this.processModelId)){
            return true;
        }
        return false;
    }
}
