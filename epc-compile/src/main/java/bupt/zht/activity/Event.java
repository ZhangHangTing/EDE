package bupt.zht.activity;

import bupt.zht.EpcObject;

/**
 * @author zhanghangting
 * @date 2018/10/12 10:46
 */
public class Event extends EpcObject{

    private String theme;
    private boolean status;
    public Event(String id, String name) {
        super(id, name);
    }

    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public boolean transferToNextActivity(){
        return false;
    }
}
