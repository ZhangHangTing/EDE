package bupt.zht.runtime;

import bupt.zht.o.OLink;
import bupt.zht.runtime.channels.LinkStatusChannel;

import java.io.Serializable;

public class LinkInfo implements Serializable {
    private static final long serialVersionUID = 1l;
    final OLink olink;
    final LinkStatusChannel pub;
    final LinkStatusChannel sub;
    LinkInfo(OLink olink,LinkStatusChannel pub,LinkStatusChannel sub){
        this.olink = olink;
        this.pub = pub;
        this.sub = sub;
    }
}
