package bupt.zht.engine;

import bupt.zht.iapi.*;
import bupt.zht.o.OProcess;
import org.dom4j.QName;

import java.util.HashMap;
import java.util.List;

public class BpelEngineImpl implements BpelEngine {
    public final HashMap<QName,BpelProcess> _activeProcess = new HashMap<>();
    private final HashMap<QName,List<BpelProcess>> _serviceMap = new HashMap<>();
    final Contexts _contexts;
    public BpelEngineImpl(Contexts contexts){
        _contexts = contexts;
    }
    boolean isProcessRegisterd(QName pid){
        return _activeProcess.containsKey(pid);
    }
    public BpelProcess getProcess(QName pid){
        return _activeProcess.get(pid);
    }
    List<BpelProcess> route(QName service,Message request){
        List<BpelProcess> routed = _serviceMap.get(service);
        return routed;
    }
    OProcess getOProcess(QName processId){
        BpelProcess process = _activeProcess.get(processId);
        if(process == null)
            return null;
        return  process.getOProcess();
    }
    @Override
    public MyRoleMessageExchange createMessageExchange(String clientKey, QName serviceId, String operation) {
        return null;
    }

    @Override
    public MessageExchange getMessageExchange(String mexId) {
        return null;
    }

    @Override
    public int getProcessThrottledMaximumCount() {
        return 0;
    }

    @Override
    public long getProcessThrottledMaximumSize() {
        return 0;
    }

    @Override
    public int getHydratedProcessCount(QName processName) {
        return 0;
    }

    @Override
    public long getHydratedProcessSize(QName processName) {
        return 0;
    }

    @Override
    public boolean dehydrateLastUnusedProcess() {
        return false;
    }

    @Override
    public void onScheduledJob(Scheduler.JobInfo jobInfo) {

    }
}
