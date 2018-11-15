/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.ode.jacob.vpu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ode.jacob.*;
import org.apache.ode.jacob.soup.*;
import org.apache.ode.utils.CollectionUtils;
import org.apache.ode.utils.ObjectPrinter;
import org.apache.ode.utils.msg.MessageBundle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * The JACOB Virtual Processing Unit ("VPU").
 *
 * @author Maciej Szefler <a href="mailto:mbs@fivesight.com" />
 */
public final class JacobVPU {
    private static final Logger __log = LoggerFactory.getLogger(JacobVPU.class);

    /**
     * Internationalization messages.
     */
    private static final JacobMessages __msgs = MessageBundle.getMessages(JacobMessages.class);

    /**
     * Thread-local for associating a thread with a VPU. Needs to be stored in a stack to allow reentrance.
     */
    static final ThreadLocal<Stack<JacobThread>> __activeJacobThread = new ThreadLocal<Stack<JacobThread>>();

    private static final Method REDUCE_METHOD;

    /**
     * Resolve the {@link JacobRunnable#run} method statically
     */
    static {
        try {
            //静态的解析方法run方法
            REDUCE_METHOD = JacobRunnable.class.getMethod("run", CollectionUtils.EMPTY_CLASS_ARRAY);
        } catch (Exception e) {
            throw new Error("Cannot resolve 'run' method", e);
        }
    }

    /**
     * Persisted cross-VPU state (state of the channels)
     */
    private ExecutionQueue _executionQueue;

    private Map<Class, Object> _extensions = new HashMap<Class, Object>();

    /**
     * Classloader used for loading object continuations.
     */
    //类加载器，加载可持续的对象
    private ClassLoader _classLoader = getClass().getClassLoader();

    private int _cycle;

    private Statistics _statistics = new Statistics();

    /**
     * The fault "register" of the VPU .
     */
    private RuntimeException _fault;
    /**
     * Default constructor.
     */
    public JacobVPU(){
    }
    /**
     * Re-hydration constructor.
     *
     * @param executionQueue previously saved execution context
     */
    public JacobVPU(ExecutionQueue executionQueue) {
        setContext(executionQueue);
    }
    /**
     * Instantiation constructor; used to initialize context with the concretion
     * of a process abstraction.
     *
     * @param context virgin context object
     * @param concretion the process
     */
    //实例化构造方法，用一个具体的process abstraction去初始化应用上下文
    // An abstraction is a parameterized process template，一个参数化的流程模板,
    // whose instantiation is termed a <em>concretion</em>. 它的实例化被称为<em>concretion</em>
    // Abstractions may define a set of bound channel names or other parameters, abstractions可以被定义成一组绑定通道或者其他参数
    // which are resolved at the time of the concretion.在concretion的时候被解析
    public JacobVPU(ExecutionQueue context, JacobRunnable concretion) {
        setContext(context);
        inject(concretion);
    }

    /**
     * Execute one VPU cycle.
     * @return <code>true</code> if the run queue is not empty after this cycle, <code>false</code> otherwise.
     */
    public boolean execute() {
        if (__log.isTraceEnabled()) {
            __log.trace(ObjectPrinter.stringifyMethodEnter("execute", CollectionUtils.EMPTY_OBJECT_ARRAY));
        }
        if (_executionQueue == null) {
            throw new IllegalStateException("No state object for VPU!");
        }
        if (_fault != null) {
            throw _fault;
        }
        if (!_executionQueue.hasReactions()) {
            return false;
        }
        //cycle: use to detect database serialization issues.
        _cycle = _executionQueue.cycle();
        //获取执行器队列中的一个Continuation
        Continuation rqe = _executionQueue.dequeueReaction();
        //将此Continuation放入Jacob线程中
       // System.out.println("在这里创建了一个JacobThreadImpl");
        JacobThreadImpl jt = new JacobThreadImpl(rqe);

        long ctime = System.currentTimeMillis();
        try {
            //记录下当前时间之久开始执行run方法
            jt.run();
        } catch (RuntimeException re) {
            _fault = re;
            throw re;
        }

        long rtime = System.currentTimeMillis() - ctime;
        //将执行时间保存到statistics中
        ++_statistics.numCycles;
        _statistics.totalRunTimeMs += rtime;
        _statistics.incRunTime(jt._targetStr, rtime);
        return true;
    }

    public void flush() {
        if (__log.isTraceEnabled()) {
            __log.trace(ObjectPrinter.stringifyMethodEnter("flush", CollectionUtils.EMPTY_OBJECT_ARRAY));
        }
        _executionQueue.flush();
    }

    /**
     * Set the state of of the VPU; this is analagous to loading a CPU with a
     * thread's context (re-hydration).
     *
     * @param executionQueue
     *            process executionQueue (state)
     */
    public void setContext(ExecutionQueue executionQueue) {
        if (__log.isTraceEnabled()) {
            __log.trace(ObjectPrinter.stringifyMethodEnter("setContext",
                    new Object[] { "executionQueue", executionQueue }));
        }
        _executionQueue = executionQueue;
        _executionQueue.setClassLoader(_classLoader);
    }

    public void registerExtension(Class extensionClass, Object obj) {
        if (__log.isTraceEnabled()) {
            __log.trace(ObjectPrinter
                    .stringifyMethodEnter("registerExtension", new Object[] {
                            "extensionClass", extensionClass, "obj", obj }));
        }
        _extensions.put(extensionClass, obj);
    }

    /**
     * Add an item to the run queue.
     * 在这里的method是run方法
     */
    public void addReaction(JacobObject jo, Method method, Object[] args, String desc) {
        if (__log.isTraceEnabled()) {
            __log.trace(ObjectPrinter.stringifyMethodEnter("addReaction",
                    new Object[] { "jo", jo, "method", method, "args", args, "desc", desc }));
        }
        Continuation continuation = new Continuation(jo, method, args);
        continuation.setDescription(desc);
        _executionQueue.enqueueReaction(continuation);
        ++_statistics.runQueueEntries;
    }

    /**
     * Get the active Jacob thread, i.e. the one associated with the current Java thread.
     */
    public static JacobThread activeJacobThread() {
        return __activeJacobThread.get().peek();
    }

    /**将一个具体表示注入到process上下文中
     * Inject a concretion into the process context. This amounts to changing the
     * process context from <code>P</code> to <code>P|Q</code> where
     * <code>P</code> is the previous process context and <code>Q</code> is
     * 在进程代数语法表示中，P|Q的意思是P进程和Q进程并行执行
     * the injected process. This method is equivalent to the parallel operator,
     * 这个方法等价于一个并行操作，但是它会利用外部的active
     * but is intended to be used from outside of an active {@link JacobThread}.
     */
    public void inject(JacobRunnable concretion) {
        if (__log.isDebugEnabled()) {
            __log.debug("injecting " + concretion);
        }
        //将一个JacobRunnable实例注册到process上下文中
        addReaction(concretion, REDUCE_METHOD, CollectionUtils.EMPTY_OBJECT_ARRAY,
                (__log.isInfoEnabled() ? concretion.toString() : null));
    }

    static String stringifyMethods(Class kind) {
        StringBuffer buf = new StringBuffer();
        Method[] methods = kind.getMethods();
        boolean found = false;

        for (Method method : methods) {
            if (method.getDeclaringClass() == Object.class) {
                continue;
            }
            if (found) {
                buf.append(" & ");
            }
            buf.append(method.getName()).append('(');
            Class[] argTypes = method.getParameterTypes();
            for (int j = 0; j < argTypes.length; ++j) {
                if (j > 0) {
                    buf.append(", ");
                }
                buf.append(argTypes[j].getName());
            }
            buf.append(") {...}");
            found = true;
        }
        return buf.toString();
    }

    static String stringify(Object[] list) {
        if (list == null) {
            return "";
        }
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < list.length; ++i) {
            if (i > 0) {
                buf.append(',');
            }
            buf.append(list[i]);
        }
        return buf.toString();
    }

    public void setClassLoader(ClassLoader classLoader) {
        _classLoader = classLoader;
        if (_executionQueue != null) {
            _executionQueue.setClassLoader(classLoader);
        }
    }

    /**
     * 转储VPU的状态，用于调试
     * Dump the state of the VPU for debugging purposes.
     */
    public void dumpState() {
        _statistics.printToStream(System.err);
        _executionQueue.dumpState(System.err);
    }

    public boolean isComplete() {
        return _executionQueue.isComplete();
    }

    //一个Continuation对象放入类中执行，这里详细描述了vpu是如何执行任务的
    private class JacobThreadImpl implements Runnable, JacobThread {
        //方法运行的主题对象，在JacobObject中
        private final JacobObject _methodBody;

        private final Object[] _args;

        private final Method _method;

        /** Text string identifying the left side of the reduction (for debug). */
        private String _source;

        /** Text string identifying the target class and method (for debug) . */
        private String _targetStr = "Unknown";

        JacobThreadImpl(Continuation rqe) {
            assert rqe != null;
            _methodBody = rqe.getClosure();
            _args = rqe.getArgs();
            _source = rqe.getDescription();
            //这里是method是JacobRunnable实现的run方法
            _method = rqe.getMethod();
            if (__log.isDebugEnabled()) {
                StringBuffer buf = new StringBuffer(_methodBody.getClass().getName());
                buf.append('.');
                buf.append(rqe.getMethod());
                _targetStr = buf.toString();
            }
        }
        public void instance(JacobRunnable template) {
            String desc = null;
            if (__log.isTraceEnabled()) {
                __log.trace(_cycle + ": " + template);
                desc = template.toString();
            }
            _statistics.numReductionsStruct++;
            addReaction(template, REDUCE_METHOD, CollectionUtils.EMPTY_OBJECT_ARRAY, desc);
        }

        public Channel message(Channel channel, Method method, Object[] args) {
            if (__log.isTraceEnabled()) {
                __log.trace(_cycle + ": " + channel + " ! "
                        + method.getName() + "(" + stringify(args) + ")");
            }
            _statistics.messagesSent++;

            SynchChannel replyChannel = null;
            // Check for synchronous methods; create a synchronization channel
            if (method.getReturnType() != void.class) {
                if (method.getReturnType() != SynchChannel.class) {
                    throw new IllegalStateException(
                            "ChannelListener method can only return SynchChannel: " + method);
                }
                replyChannel = (SynchChannel) newChannel(SynchChannel.class, "", "Reply Channel");
                Object[] newArgs = new Object[args.length + 1];
                //实现数组的复制
                System.arraycopy(args, 0, newArgs, 0, args.length);
                newArgs[args.length] = replyChannel;
                args = newArgs;
            }
            CommChannel chnl = (CommChannel) ChannelFactory.getBackend(channel);
            CommGroup grp = new CommGroup(false);
            CommSend send = new CommSend(chnl, method, args);
            grp.add(send);
            _executionQueue.add(grp);
            return replyChannel;
        }
        //通过工厂方式新建一个通道，之后返回此通道
        public Channel newChannel(Class channelType, String creator, String description) {
            //创建一个通信通道对象（通道继承于ExecutionQueueObject）
            CommChannel chnl = new CommChannel(channelType);
            chnl.setDescription(description);
            //将此通信通道添加到执行队列之中
            _executionQueue.add(chnl);
            //传入通信通道以及通道类型，利用通道工厂创建出一个通道对象
            Channel ret = ChannelFactory.createChannel(chnl, channelType);
            if (__log.isTraceEnabled())
                __log.trace(_cycle + ": new " + ret);

            _statistics.channelsCreated++;
            return ret;
        }

        public String exportChannel(Channel channel) {
            if (__log.isTraceEnabled()) {
                __log.trace(_cycle + ": export<" + channel + ">");
            }
            CommChannel chnl = (CommChannel) ChannelFactory.getBackend(channel);
            return _executionQueue.createExport(chnl);
        }

        public Channel importChannel(String channelId, Class channelType) {
            CommChannel cframe = _executionQueue.consumeExport(channelId);
            return ChannelFactory.createChannel(cframe, channelType);
        }
        //VPU实现了JacobThread的object最关键方法
        //在进程代数中，当replicate == false为这段代码实现了 x ? { ChannelListener }语义
        public void object(boolean replicate, ChannelListener[] ml) {
            //将所有的通道监听器的信息都添加到日志中去
            if (__log.isTraceEnabled()) {
                StringBuffer msg = new StringBuffer();
                msg.append(_cycle);
                msg.append(": ");
                for (int i = 0; i < ml.length; ++i) {
                    if (i != 0) msg.append(" + ");
                    msg.append(ml[i].getChannel());
                    msg.append(" ? ");
                    msg.append(ml.toString());
                }
                __log.debug(msg.toString());
            }
            //将VPU执行队列中的Continuations数量加1
            _statistics.numContinuations++;

            CommGroup grp = new CommGroup(replicate);
            for (int i = 0; i < ml.length; ++i) {
                CommChannel chnl = (CommChannel) ChannelFactory
                        .getBackend(ml[i].getChannel());
                // oframe.setDebugInfo(fillDebugInfo());
                CommRecv recv = new CommRecv(chnl, ml[i]);
                grp.add(recv);
            }
            _executionQueue.add(grp);
        }

        public void object(boolean replicate, ChannelListener methodList)
                throws IllegalArgumentException {
            object(replicate, new ChannelListener[] { methodList });
        }

        /* UNUSED
         private DebugInfo fillDebugInfo() {
            // Some of the debug information is a bit lengthy, so lets not put
            // it in all the time... eh.
            DebugInfo frame = new DebugInfo();
            frame.setCreator(_source);
            Exception ex = new Exception();
            StackTraceElement[] st = ex.getStackTrace();
            if (st.length > 2) {
                StackTraceElement[] stcut = new StackTraceElement[st.length - 2];
                System.arraycopy(st, 2, stcut, 0, stcut.length);
                frame.setLocation(stcut);
            }
            return frame;
        }
        */

        public Object getExtension(Class extensionClass) {
            return _extensions.get(extensionClass);
        }

        public void run() {
            assert _methodBody != null;
            assert _method != null;
            //检查_methodBody的类信息是否可以分配到_method类对应的对象中
            assert _method.getDeclaringClass().isAssignableFrom(_methodBody.getClass());

            if (__log.isTraceEnabled()) {
                __log.trace(_cycle + ": " + _source);
            }
            //Object类是所有对象的基类，声明一个Object对象，方便之后可以向下转型成任意的通道类型
            Object[] args;
            SynchChannel synchChannel;
            //反射中的getReturnType表示返回一个对应的class对象，该对象表示此Method对象表示的方法的正式返回类型
            if (_method.getReturnType() != void.class) {
                System.out.println("_method的名字:" + _method.getName());
                args = new Object[_args.length - 1];
                //实现数组的拷贝，将Continuation中的Object数组复制
                System.arraycopy(_args, 0, args, 0, args.length);
                //利用这个对象数组声明一个同步信通道对象，在这里并没有创建该通道对象
                synchChannel = (SynchChannel) _args[args.length];
            } else {
                args = _args;
                synchChannel = null;
            }
            //保存线程执行现场
            stackThread();
            long ctime = System.currentTimeMillis();
            try {
                //java动态代理模式，调用invoke方法，传入被代理对象和参数，将请求转发给被代理对象
                //在这里被代理对象是_methodBody，也就是最后将参数args放入_methodBody中执行_method表示的方法
                //从上面可以看到，这个参数是一个synchChannel（一个同步通信通道）
                //程序在执行的时候，一开始的_method方法都是run
                //到后来孩子通道完成并且通知父亲活动的时候，_method方法就变成通道的方法
                System.out.println("_method表示的方法："+_method.getName());
                _method.invoke(_methodBody, args);
                //当调用通道的方法的时候，synchChannel就不为null（这个是由ChannelFactory创建的），接着就会执行ret方法
                if (synchChannel != null) {
                    //执行同步通道的ret方法，通过这个通道来控制各个线程之间的同步
                    System.out.println("synchChannel不为null");
                    synchChannel.ret();
                }
            } catch (IllegalAccessException iae) {
                String msg = __msgs.msgMethodNotAccessible(_method.getName(),
                        _method.getDeclaringClass().getName());
                __log.error(msg, iae);
                throw new RuntimeException(msg, iae);
            } catch (InvocationTargetException e) {
                if (e.getTargetException() instanceof RuntimeException) {
                    throw (RuntimeException) e.getTargetException();
                } else {
                    String msg = __msgs.msgClientMethodException(_method.getName(),
                            _methodBody.getClass().getName());
                    __log.error(msg, e.getTargetException());
                    throw new RuntimeException(e.getTargetException());
                }
            } finally {
                //最后将操作时间记录下来
                ctime = System.currentTimeMillis() - ctime;
                _statistics.totalClientTimeMs += ctime;
                unstackThread();
            }
        }

        public String toString() {
            return "PT[ " + _methodBody + " ]";
        }

        private void stackThread() {
            //_activeJacobThread是一个线程本地变量，为每一个使用该变量的线程提供独立的线程副本，
            // 每一个线程都可以独立的改变自己的副本，而不会影响其他线程对应的副本
            //在这里，每一个线程访问的自己的变量是一个包含JacobThread的栈
            //首先从线程本地变量中
            Stack<JacobThread> currStack = __activeJacobThread.get();
            if (currStack == null) {
                //ThreadLocal的使用方法，必须先set再get
                currStack = new Stack<JacobThread>();
                __activeJacobThread.set(currStack);
            }
            //将当前的JacobThread实现线程保存到栈中
            currStack.push(this);
        }
        private JacobThread unstackThread() {
            Stack<JacobThread> currStack = __activeJacobThread.get();
            assert currStack != null;
            return currStack.pop();
        }
    }

}
