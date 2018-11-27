package bupt.zht.activity;

/**
 * @author zhanghangting
 * @date 2018/11/9 11:07
 */
public class And extends LogicUnit {

    private boolean firstComing;
    private boolean nextComing;

    public And(String id, String type) {
        super(id, type);
        firstComing = false;
        nextComing = false;
    }
    @Override
    public void doLogicExpression() {
    }
    @Override
    public boolean isAlive(){
        return (firstComing && nextComing);
    }
    public void setFirstComing(boolean firstComing) {
        this.firstComing = firstComing;
    }

    public void setNextComing(boolean nextComing) {
        this.nextComing = nextComing;
    }
    public boolean isFirstComing() {
        return firstComing;
    }

    public boolean isNextComing() {
        return nextComing;
    }
}
