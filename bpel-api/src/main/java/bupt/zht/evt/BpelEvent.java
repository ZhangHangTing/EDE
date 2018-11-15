package bupt.zht.evt;

import java.io.Serializable;
import java.util.Date;

public abstract class BpelEvent implements Serializable {
    public enum TYPE{
        dataHandling,activityLifecycle,scopeHandling,instanceLifecycle,correlation
    }
    public transient EventContext eventContext;
    private Date _timestamp = new Date();
    private int _lineNo = -1;

    public void setTimestamp(Date _timestamp) {
        this._timestamp = _timestamp;
    }

    public Date getTimestamp() {
        return _timestamp;
    }

    public int getLineNo(){

        return _lineNo;

    }
    public void setLineNo(int linkNo){
        _lineNo = linkNo;
    }
    public static String eventName(BpelEvent event){
        String name = event.getClass().getName();
        return name.substring(name.lastIndexOf(".") + 1);
    }
    public abstract TYPE getType();
}
