package bupt.zht;

import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * @author zhanghangting
 * @date 2018/10/17 9:21
 */
public class EpcObject implements Serializable{

    private String id;
    private String name;
    public EpcObject(){}
    public EpcObject(String id, String name){
        this.id = id;
        this.name = name;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public boolean equals(Object object){
        if(object instanceof EpcObject ){
            EpcObject anotherEpcObject = (EpcObject) object;
            if(this.id.equals(anotherEpcObject.getId()))
                return true;
        }
        return false;
    }
    @Override
    public int hashCode(){
        return 1;
    }
}
