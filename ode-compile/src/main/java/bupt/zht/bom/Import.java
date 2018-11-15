package bupt.zht.bom;



import org.dom4j.Element;

import java.net.URI;
import java.net.URISyntaxException;

public class Import extends BpelObject {
    public Import(Element el) {
        super(el);
    }
    public String getNamespace(){
        return getAttribute("namespace");
    }
    public URI getLocation(){
        String loc = getAttribute("location");
        try {
            return new URI(loc);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public String getImportType(){
        return getAttribute("importType");
    }
}
