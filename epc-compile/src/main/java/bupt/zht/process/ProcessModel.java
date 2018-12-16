package bupt.zht.process;

import bupt.zht.EpcObject;
import java.util.List;

/**
 * @author zhanghangting
 * @date 2018/12/15 20:58
 */
public class ProcessModel {

    // 通过modelMark这个标志来确定属于哪一个流程模型
    private String modelMark;
    // 流程模型Id唯一标识一个流程模型，该值通过计算modelMark的hashcode值得到
    private String processModelId;
    private List<EpcObject> modelEpcObjectList;

    public ProcessModel(String modelMark,List<EpcObject> modelEpcObjectList){
        this.modelMark = modelMark;
        this.processModelId = String.valueOf(modelMark.hashCode());
        this.modelEpcObjectList = modelEpcObjectList;
    }

    public String getModelMark() {
        return modelMark;
    }

    public void setModelMark(String modelMark) {
        this.modelMark = modelMark;
    }

    public String getProcessModelId() {
        return processModelId;
    }

    public void setProcessModelId(String processModelId) {
        this.processModelId = processModelId;
    }

    public List<EpcObject> getModelEpcObjectList() {
        return modelEpcObjectList;
    }

    public void setModelEpcObjectList(List<EpcObject> modelEpcObjectList) {
        this.modelEpcObjectList = modelEpcObjectList;
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
