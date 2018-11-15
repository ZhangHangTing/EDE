package org.apache.ode.jacob.test;

import org.apache.ode.jacob.JacobRunnable;
import org.apache.ode.jacob.SynchChannel;
import org.apache.ode.jacob.SynchChannelListener;

//第五步，实现一个调用(JacobRunnable的子类）类
public class MainObject extends JacobRunnable{
    private static final long serialVersionUID = 1L;

    private String name = null;

    public MainObject(String name)
    {

        this.name=name;

    }
    @Override
    public void run() {

        final TestChannel channel = newChannel(TestChannel.class);
        instance(new TestObject(channel));
        SynchChannel sc = channel.print(name + ":" + "1");
        object(new SynchChannelListener(sc) {
                   public void ret() {
                       System.out.println("Hello:I am "+ name);
                object(new SynchChannelListener(channel.print(name + ":" + "2")) {
                    public void ret() {
                        object(new SynchChannelListener(channel.print(name + ":" + "3")) {
                            public void ret() {
                                object(new SynchChannelListener(channel.print(name + ":" + "4")) {
                                    public void ret() {
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
}
