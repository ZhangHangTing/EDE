package bupt.zht.activity;

import bupt.zht.EpcObject;

/**
 * @author zhanghangting
 * @date 2018/10/29 11:01
 */
public class DivideLogicUnit extends LogicUnit {

    private EpcObject nextObject;

    public DivideLogicUnit(String id, String name) {
        super(id, name);
    }

    @Override
    public void doLogicExpression() {
        if(logicExpression.equals("AND")){

        }
    }

    public EpcObject getNextObject() {
        return nextObject;
    }
    public void setNextObject(EpcObject nextObject) {
        this.nextObject = nextObject;
    }
}
