package bupt.zht.bom;


import org.dom4j.Element;

public class Process extends Scope {
    //对于每一个标签类，初始化的时候首先调用父类的构造方法，先对父类进行初始化
    public Process(Element el){
        super(el);
    }
    public Activity getRootActivity(){
        return getFirstChild(Activity.class);
    }
    public String getName(){
        return getAttribute("name");
    }
}
