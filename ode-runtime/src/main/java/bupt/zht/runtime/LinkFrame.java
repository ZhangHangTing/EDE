package bupt.zht.runtime;

import bupt.zht.o.OLink;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LinkFrame implements Serializable {
    private static final long serialVersionUID = 1l;
    LinkFrame next;
    Map<OLink,LinkInfo> links = new HashMap<>();
    public LinkFrame(LinkFrame next){
        this.next = next;
    }
    LinkInfo resolve(OLink link){
        LinkInfo li = links.get(link);
        if(li == null && next != null){
            return next.resolve(link);
        }
        return li;
    }
}
