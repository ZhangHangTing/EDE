package bupt.zht.runtime.channels;

import org.apache.ode.jacob.ChannelListener;

public abstract class ParentScopeChannelListener extends ChannelListener<ParentScopeChannel> implements  ParentScope{
    protected ParentScopeChannelListener(ParentScopeChannel channel) throws IllegalStateException {
        super(channel);
    }
}
