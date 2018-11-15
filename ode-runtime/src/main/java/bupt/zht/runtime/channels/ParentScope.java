package bupt.zht.runtime.channels;

import bupt.zht.o.OScope;
import bupt.zht.runtime.CompensationHandler;
import org.apache.ode.jacob.SynchChannel;
import org.dom4j.Element;

import java.util.Set;

public interface ParentScope {
    void compenstae(OScope scope, SynchChannel ret);
    void completed(FaultData faultData, Set<CompensationHandler> compensations);
    void cancelled();
    void failure(String reason, Element data);
}
