package org.apache.ode.jacob.test;

import org.apache.ode.jacob.JacobRunnable;
import org.apache.ode.jacob.SynchChannel;

import java.util.Date;

//第四步，实现一个被调用（JacobRunnable）类的子类（TestObject）
public class TestObject extends JacobRunnable{
    private static final long serialVersionUID = -8449707873233976113L;

    private TestChannel channel;

    public TestObject(TestChannel channel)
    {

        this.channel=channel;

    }
    @Override
    public void run() {
        object(true, new TestChannelListener(this.channel) {
            private static final long serialVersionUID = 6861624872661628447L;
            public SynchChannel print(String str) {
                System.out.println(new Date() + ":" +str);
                return null;
            }
        });
    }
}
