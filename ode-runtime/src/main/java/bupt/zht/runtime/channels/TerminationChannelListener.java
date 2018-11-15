package bupt.zht.runtime.channels;

import org.apache.ode.jacob.ChannelListener;

public abstract class TerminationChannelListener extends ChannelListener<TerminationChannel> implements Termination{
    protected TerminationChannelListener(TerminationChannel channel) throws IllegalStateException {
        super(channel);
    }
}
