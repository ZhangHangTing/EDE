package bupt.zht.bom;


import org.dom4j.Element;
import java.util.Collections;
import java.util.List;


public class Activity extends BpelObject {
    public Activity(Element el){
        super(el);
    }
    public String getName(){
        return getAttribute("name");
    }
    public List<LinkSource> getLinkSource(){
        Sources sources = getFirstChild(Sources.class);
        if(sources == null)
            return Collections.emptyList();
        return sources.getChildren(LinkSource.class);

    }
    public List<LinkTarget> getLinkTargets(){
        Targets targets = getFirstChild(Targets.class);
        if(targets == null)
            return Collections.emptyList();
        return targets.getChildren(LinkTarget.class);
    }
}
