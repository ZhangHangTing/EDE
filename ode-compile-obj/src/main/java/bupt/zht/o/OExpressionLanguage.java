package bupt.zht.o;

import java.util.HashMap;
import java.util.Map;

public class OExpressionLanguage extends OBase {
    private static final long serialVersionUID = 1L;
    public String expressionLanguageUri;
    public final Map<String,String> properties = new HashMap<String, String>();
    public OExpressionLanguage(OProcess owner,Map<String,String> properties){
        super(owner);
        if(properties != null)
            this.properties.putAll(properties);
    }
    public boolean equals(Object obj){
        if(obj instanceof OExpressionLanguage)
            return ((OExpressionLanguage)obj).expressionLanguageUri.equals(expressionLanguageUri);
        else
            return super.equals(obj);
    }
    public int hashCode(){
        return expressionLanguageUri.hashCode();
    }

}
