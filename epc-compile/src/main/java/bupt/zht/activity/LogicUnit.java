package bupt.zht.activity;

import bupt.zht.EpcObject;

import java.util.List;

/**
 * @author zhanghangting
 * @date 2018/10/23 9:26
 */
public abstract class LogicUnit extends EpcObject {
    // 逻辑节点拥有判断逻辑表达的功能，这里的表达式类型暂时用字符串代替
    protected String logicExpression;
    public LogicUnit(String id, String type) {
        super(id, type);
    }
    public String getLogicExpression() {
        return logicExpression;
    }
    public void setLogicExpression(String logicExpression) {
        this.logicExpression = logicExpression;
    }
    public abstract void doLogicExpression();
}
