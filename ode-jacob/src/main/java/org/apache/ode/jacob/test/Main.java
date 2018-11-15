package org.apache.ode.jacob.test;

import org.apache.ode.jacob.vpu.ExecutionQueueImpl;
import org.apache.ode.jacob.vpu.JacobVPU;

public class Main {
    public static void  main(String[] args){
        //初始化一个JacobVPU对象的时候，必须传入一个运行队列的实现类，JacobRunnable可以选择传入
        JacobVPU vpu = new JacobVPU(new ExecutionQueueImpl(null),new MainObject("Main 1"));
        //也可以通过inject的方式将新的JacobRunnable任务放入vpu的运行队列中
        //当一个JacobRunnable对象注册到vpu中的时候，它实际上是被注册作为一个Continuation对象
        vpu.inject(new MainObject("Main 2"));
        vpu.inject(new MainObject("Main 3"));
        vpu.inject(new MainObject("Main 4"));
        while(vpu.execute()){}
//        vpu.execute();
    }
}
