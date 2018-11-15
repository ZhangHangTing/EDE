package org.apache.ode.jacob.bpeltest;

import org.apache.ode.jacob.JacobRunnable;
import org.apache.ode.jacob.vpu.ExecutionQueueImpl;
import org.apache.ode.jacob.vpu.JacobVPU;

public class  Main {
    public static  void  main(String[] args){
        ExecutionQueueImpl soup = new ExecutionQueueImpl(null);
        JacobVPU vpu = new JacobVPU(soup,new Process());
        //周期性的执行vpu任务，如果当前周期执行完之后，执行队列不为空，则返回true
        while(vpu.execute()){
        }
    }
    //实现一个被调用类的子类
    static class INVOKE extends JacobRunnable {
        //通道用于交流message
        private DemoChannel _channel;
        public INVOKE(DemoChannel channel) {
            _channel = channel;
        }

        @Override
        public void run() {
            System.out.println("INVOKE Activity!");
            DemoChannel demoChannel12 = newChannel(DemoChannel.class, "demo2");
            instance(new RECEIVE(demoChannel12));
            //创建一个对象，通道调用完成之后回调此通道方法
            object(new DemoChannelListener(demoChannel12) {
                public void onSuccess(String successInfo) {
                    System.out.println("INVOKE successInfo:"+successInfo);
                    //最后任务完场之后，通过通道传递消息
                    _channel.onSuccess("INVOKE Done...");
                }
                public void onFailure(String errorString) {
                    System.out.println("errorString" + errorString);
                }
            });
        }
        }
        static class RECEIVE extends JacobRunnable{
        private DemoChannel _demoChannel;
        public RECEIVE(DemoChannel demoChannel){
            _demoChannel = demoChannel;
        }
        @Override
            public void run() {
            System.out.println("RECEIVE Activity!");
            _demoChannel.onSuccess("RECEIVE success...");
            }
        }
        static class Process extends JacobRunnable{
            @Override
            public void run() {
                DemoChannel channel1 = newChannel(DemoChannel.class,"demo");
                //实例化INVOKE对象，也就是将INVOKE对象最终添加到soup中去
                instance(new INVOKE(channel1));
                object(new DemoChannelListener(channel1) {
                    public void onSuccess(String successInfo) {
                       //这里添加对应方法的处理逻辑，处理完成之后通过DemoChannelListener通道通知其父亲
                    }
                    public void onFailure(String errorString) {
                        System.out.println("errorString:"+errorString);
                    }
                });
            }
        }
}
