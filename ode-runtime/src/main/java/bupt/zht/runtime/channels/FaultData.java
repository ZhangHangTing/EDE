package bupt.zht.runtime.channels;

import bupt.zht.o.OBase;
import bupt.zht.o.OVarType;
import org.apache.ode.utils.SerializableElement;
import org.dom4j.Element;
import org.dom4j.QName;

import java.io.Serializable;

public class FaultData implements Serializable {
    private QName _faultName;
    private OVarType _faultVayType;
    private SerializableElement _faultMsg;
    private OBase _location;
    private final String _explanation;
    public FaultData(QName fault, OBase location,String explanation){
        _faultName = fault;
        _location = location;
        _explanation = explanation;
    }
    public FaultData(QName fault, Element faultMsg,OVarType faultVayType,OBase location){
        this(fault,location,null);
        _faultMsg = new SerializableElement(faultMsg);
        _faultVayType = faultVayType;
    }
    public Element getFaultMessage(){
        return (_faultMsg == null) ? null : _faultMsg.getElement();
    }
    public OVarType getFaultVayType(){
        return _faultVayType;
    }
    public QName getFaultName(){
        return _faultName;
    }
    public int getFaultLineNo(){
        return findLineNo(_location);
    }

    public String getExplanation() {
        return _explanation;
    }
    public int getActivityId(){
        return _location.getId();
    }
    protected int findLineNo(OBase location){
        if(location == null || location.debugInfo == null)
            return -1;
        return location.debugInfo.startLine;
    }
}
