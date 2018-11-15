package org.apache.ode.jacob.test;

import org.apache.ode.jacob.ChannelListener;

//第三步，定义通道监听器接口
public abstract class TestChannelListener extends ChannelListener<TestChannel> implements Test{
    private static final long serialVersionUID = -1186997267517660170L;
    protected TestChannelListener(TestChannel channel) throws IllegalStateException {
        super(channel);
    }
}
