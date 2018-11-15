package bupt.zht.runtime;

import org.apache.ode.jacob.JacobRunnable;
import org.apache.ode.jacob.vpu.JacobVPU;

public abstract class BpelJacobRunnable extends JacobRunnable {
    protected BpelRuntimeContext getBpelRuntimeContext(){
        BpelRuntimeContext nativeApi = (BpelRuntimeContext) JacobVPU.activeJacobThread().getExtension(BpelRuntimeContext.class);
        return nativeApi;
    }
    protected JacobRunnable createChild(ActivityInfo childInfo,ScopeFrame scopeFrame,LinkFrame linkFrame){
        return new ACTIVITYGUARD(childInfo,scopeFrame,linkFrame);
    }
    protected long genMonotonic(){
        return getBpelRuntimeContext().getPid();
    }
}
