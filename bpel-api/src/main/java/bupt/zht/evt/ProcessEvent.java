package bupt.zht.evt;

import org.dom4j.QName;

public class ProcessEvent extends BpelEvent {
    private QName _processId;
    private QName _processname;
    public ProcessEvent(){}

    public QName get_processId() {
        return _processId;
    }

    public void setProcessId(QName _processId) {
        this._processId = _processId;
    }

    public void setGet_processname(QName _processname) {
        this._processname = _processname;
    }

    public QName getProcessname() {
        return _processname;
    }

    @Override
    public TYPE getType() {
        return null;
    }
}
