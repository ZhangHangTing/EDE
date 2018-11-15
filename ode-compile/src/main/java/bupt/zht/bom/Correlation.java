package bupt.zht.bom;


import org.dom4j.Element;
import java.util.HashMap;
import java.util.Map;

public class Correlation extends BpelObject {
    public Correlation(Element el){
        super(el);
    }
    public enum Initiate{
        YES,NO,JOIN,UNSET;
        private static final Map<String, Initiate> _map = new HashMap<String,Initiate>();
        static {
            _map.put("yes",YES);
            _map.put("no",NO);
            _map.put("join",JOIN);
            _map.put("",UNSET);
        }
    }
    public enum CorrelationPattern{
        IN,OUT,INOUT,UNSET;
        private static final Map<String,CorrelationPattern> _map = new HashMap<String,CorrelationPattern>();
        static {
            _map.put("in",IN);
            _map.put("response",IN);
            _map.put("out",OUT);
            _map.put("request",OUT);
            _map.put("in-out",INOUT);
            _map.put("out-in",INOUT);
            _map.put("request-response",INOUT);
            _map.put("",UNSET);
        }
    }
    public String getCorrelation(){
        return getAttribute("set");
    }
//    public Initiate getInitiate(){
//        return getAttribute("initiate");
//    }
}
