package bupt.zht.bom;



import org.dom4j.Element;

import java.util.Collections;
import java.util.List;

public class Scope extends BpelObject {
    public Scope(Element el){
        super(el);
    }
    public List<Variable> getVariable(){
        BpelObject vars = getFirstChild(Variable.class);
        if(vars == null)
            return Collections.emptyList();
        return vars.getChildren(Variable.class);
    }
}
