package bupt.zht.runtime;

import bupt.zht.o.OReply;
import bupt.zht.o.OScope;
import bupt.zht.runtime.channels.FaultData;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.Iterator;

public class REPLY extends ACTIVITY {

    public REPLY(ActivityInfo self, ScopeFrame scopeFrame, LinkFrame linkFrame) {
        super(self, scopeFrame, linkFrame);
    }

    @Override
    public void run() {
        final OReply oreply = (OReply) _self.o;
        FaultData fault = null;
        sendVariableReadEvent(_scopeFrame.resolve(oreply.variable));
        Node msg = fetchVariableData(_scopeFrame.resolve(oreply.variable),false);
        for(Iterator<OScope.CorrelationSet> i = oreply.initCorrelations.iterator(); i .hasNext();){

        }
        for(OScope.CorrelationSet aJoinCOrrelation : oreply.joinCorrelations){

        }
        //这一步是真正的发送返回消息,操作这里使用的是javax下wsdl包中的operation
        getBpelRuntimeContext().reply(_scopeFrame.resolve(oreply.partnerLink),oreply.operation.getName(),oreply.messageExchangeId,
                (Element)msg,(oreply.fault != null) ? oreply.fault : null);
        _self.parent.completed(fault,CompensationHandler.emptySet());
    }
}
