package org.apache.ode.jacob.bpeltest;

import org.apache.ode.jacob.ChannelListener;

public abstract class DemoChannelListener  extends ChannelListener<DemoChannel> implements Demo{
    protected DemoChannelListener(DemoChannel channel) throws IllegalStateException {
        super(channel);
    }
}
