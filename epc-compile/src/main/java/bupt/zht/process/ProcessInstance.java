package bupt.zht.process;

import bupt.zht.activity.Function;

import java.util.List;

/**
 * @author zhanghangting
 * @date 2018/12/15 20:58
 */
public class ProcessInstance {

    // 通过instanceMark的标志来确定属于哪一个流程实例
    private String instanceMark;
    private String processModelId;
    private String processInstanceId;

    private List<Function> processFunctionList;
}
