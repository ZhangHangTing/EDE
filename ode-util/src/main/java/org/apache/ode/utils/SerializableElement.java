package org.apache.ode.utils;

import org.dom4j.Element;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class SerializableElement implements Serializable {
    private static final long serialVersionUID = -1l;
    private transient Element e;
    public SerializableElement(Element e){
        this.e = e;
    }
    public Element getElement() {
        return e;
    }
    private void readObject(ObjectInputStream in) throws IOException,ClassNotFoundException{
    }
    private void writeObject(ObjectInputStream out)throws IOException{
    }
}
