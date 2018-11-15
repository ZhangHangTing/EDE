package bupt.zht.dao;

import bupt.zht.common.CorrelationKey;
import org.dom4j.QName;

import java.util.Collection;

public interface ProcessDAO {
    QName getProcessId();
    QName getType();
    void instanceCompleted(ProcessInstanceDAO instance);
    String getGuid();
    int getNumInstances();
    Collection<ProcessInstanceDAO> getActiveInstances();
    Collection<ProcessInstanceDAO> findInstance(CorrelationKey ckey, short processInstanceState);
}
