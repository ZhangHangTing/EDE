package bupt.zht.activity;

/**
 * @author zhanghangting
 * @date 2018/10/29 10:24
 */
public class MergeLogicUnit extends LogicUnit {

    private Event eventOne;
    private Event eventTwo;

    public MergeLogicUnit(String id, String name) {
        super(id, name);
    }

    @Override
    public void doLogicExpression() {

    }

    public Event getEventOne() {
        return eventOne;
    }

    public void setEventOne(Event eventOne) {
        this.eventOne = eventOne;
    }

    public Event getEventTwo() {
        return eventTwo;
    }

    public void setEventTwo(Event eventTwo) {
        this.eventTwo = eventTwo;
    }
}
