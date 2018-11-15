package bupt.zht.common;

import sun.util.resources.ca.CalendarData_ca;

import java.io.Serializable;

public class CorrelationKey implements Serializable {
    private String _csetName;
    private String _keyValues[];
    public CorrelationKey(String csetName,String[] keyValues){
        _csetName = csetName;
        _keyValues =keyValues;
    }
}
