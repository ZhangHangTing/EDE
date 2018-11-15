package bupt.zht.runtime;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

public class CompensationHandler implements Serializable,Comparable<CompensationHandler> {

    static Set<CompensationHandler> emptySet(){
        return Collections.emptySet();
    }
    @Override
    public int compareTo(CompensationHandler o) {
        return 0;
    }
}
