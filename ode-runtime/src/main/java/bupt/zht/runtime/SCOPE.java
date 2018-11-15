package bupt.zht.runtime;

import bupt.zht.evt.ScopeStartEvent;
import bupt.zht.o.OBase;
import bupt.zht.o.OScope;
import bupt.zht.runtime.channels.*;
import org.apache.ode.jacob.ChannelListener;
import org.apache.ode.jacob.SynchChannel;
import org.dom4j.Element;
import org.omg.PortableInterceptor.ACTIVE;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SCOPE extends ACTIVITY{
    private OScope _oscope;
    private ActivityInfo _child;
    private Set<EventHandlerInfo> _eventHandlers = new HashSet<>();
    public SCOPE(ActivityInfo self,ScopeFrame frame,LinkFrame linkFrame){
        super(self,frame,linkFrame);
        _oscope = (OScope) self.o;
    }
    static final class EventHandlerInfo implements Serializable{
        private static final long serialVersion = -1l;
        final OBase o;
        final EventHandlerControlChannel cc;
        final ParentScopeChannel psc;
        final TerminationChannel tc;
        boolean terminateRequested;
        boolean stopRequested;
        EventHandlerInfo(OBase o,EventHandlerControlChannel cc,ParentScopeChannel psc,TerminationChannel tc) {
            this.o = o;
            this.cc = cc;
            this.psc = psc;
            this.tc = tc;
        }
    }
    //这里具体实现scope标签应该执行的任务
    @Override
    public void run() {
        //启动孩子活动
        _child = new ActivityInfo(genMonotonic(),_oscope.activity,newChannel(ParentScopeChannel.class));
        instance(createChild(_child,_scopeFrame,_linkFrame));
        //中间还有一个事件处理机制
        getBpelRuntimeContext().initializePartnerLinks(_scopeFrame.scopeInstanceId,_oscope.partnerLinks.values());
        sendEvent(new ScopeStartEvent());
        instance(new ACTIVE());
    }
    class ACTIVE extends ACTIVITY{
        private static final long serialVersionUID = -5l;
        private boolean _terminated;
        private long _startTime;
        private boolean _childTermRequested;
        ACTIVE(){
            super(SCOPE.this._self,SCOPE.this._scopeFrame,SCOPE.this._linkFrame);
            _startTime = System.currentTimeMillis();
        }
        @Override
        public void run() {
            if(_child != null || !_eventHandlers.isEmpty()){
                HashSet<ChannelListener> mlSet = new HashSet<>();
                //监听来自父亲的消息
                mlSet.add(new TerminationChannelListener(_self.self) {
                    @Override
                    public void terminate() {
                        _terminated = true;
                        //将中断的请求发送到嵌套活动之中
                        if(_child != null && !_childTermRequested){
                            replication(_child.self).terminate();
                            _childTermRequested = true;
                        }
                        //将中断请求发送至我们的事件处理器中
                        terminateEventHandlers();
                        instance(ACTIVE.this);
                    }
                });
                //如果孩子节点依旧保持存活，则处理来自孩子节点的消息
                if(_child != null){
                    mlSet.add(new ParentScopeChannelListener(_child.parent) {

                        @Override
                        public void compenstae(OScope scope, SynchChannel ret) {
                        }
                        //完成方法,这个方法要着重强调
                        @Override
                        public void completed() {

                        }

                        @Override
                        public void cancelled() {
                        }

                        @Override
                        public void failure(String reason, Element data) {

                        }
                    });
                }
            }
            //没有什么活动可以等待了，任何可用的但是没有被激活的补偿处理器将被遗忘
            else{

            }
        }
        private void terminateEventHandlers(){
            for(Iterator<EventHandlerInfo> i = _eventHandlers.iterator(); i.hasNext();){
                EventHandlerInfo ehi = i.next();
                if(!ehi.terminateRequested && !ehi.stopRequested){
                    replication(ehi.tc).terminate();
                    ehi.terminateRequested = true;
                }
            }
        }
    }
    protected long genMonotonic(){
        return getBpelRuntimeContext().getPid();
    }
}
