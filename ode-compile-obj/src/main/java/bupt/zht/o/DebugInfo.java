package bupt.zht.o;

import org.dom4j.QName;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

//这个类中包含了有关于创建编译对象的源信息
public class DebugInfo implements Serializable{
    static final long serivalVersionUID = -1L;
    public String sourceURI;
    public int startLine;
    public int endLine;
    public String description;
    //public HashMap<QName,Object> extensibilityElements = new HashMap<QName, Object>();
    public DebugInfo(String sourceURI, int startLine, int endLine){
        this.sourceURI = sourceURI;
        this.startLine = startLine;
        this.endLine = endLine;
    }
    public DebugInfo(String sourceURI,int line){
        this(sourceURI,line,line);
    }
    private void readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException{
        ObjectInputStream.GetField getField = ois.readFields();
        sourceURI = (String)getField.get("sourceURI",null);
        startLine = (Integer)getField.get("startLine",0);
        endLine = (Integer)getField.get("endLine",0);
    }

}
