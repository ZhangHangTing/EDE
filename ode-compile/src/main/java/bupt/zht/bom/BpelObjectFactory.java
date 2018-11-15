package bupt.zht.bom;

import org.dom4j.Element;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class BpelObjectFactory {
    private static BpelObjectFactory _instance = new BpelObjectFactory();
    private final Map<String,Class<? extends  BpelObject>> _mappings = new HashMap<String, Class<? extends BpelObject>>();
    private Class[] _CTOR = {Element.class};
    //构造函数先初始化标签名称和名称对应的类信息
    public BpelObjectFactory(){
        _mappings.put(Bpel20QName.FINAL_PROCESS,Process.class);
        _mappings.put(Bpel20QName.FINAL_SOURCES,Sources.class);
        _mappings.put(Bpel20QName.FINAL_TARGETS,Targets.class);
        _mappings.put(Bpel20QName.FINAL_SOURCES,BpelObject.class);
        _mappings.put(Bpel20QName.FINAL_TARGETS,BpelObject.class);
        _mappings.put(Bpel20QName.FINAL_SOURCE,LinkSource.class);
        _mappings.put(Bpel20QName.FINAL_TARGET,LinkTarget.class);
        _mappings.put(Bpel20QName.FINAL_VARIABLE,Variable.class);
        _mappings.put(Bpel20QName.FINAL_VARIABLES,Variables.class);
        _mappings.put(Bpel20QName.FINAL_TO,To.class);
        _mappings.put(Bpel20QName.FINAL_FROM,From.class);
        _mappings.put(Bpel20QName.FINAL_COPY,Copy.class);
    }
    public static BpelObjectFactory getInstance(){
        return _instance;
    }

    public BpelObject createBpelObject(Element el,URI uri){
        //根据标签名称获取对应的类信息
        //映射表当中保存了Bpel2.0规范的标签名称，以及对应的标签类信息
        //通过标签名称获取标签类信息
        String name = el.getName();
        Class cls = _mappings.get(name);
        if(cls == null){
            return new BpelObject(el);
        }
        System.out.println("cls:" + cls);
        try {
            //根据此类信息，利用java反射的方式创建一个BeplObject对象，并返回
            Constructor ctor = cls.getConstructor(Element.class);
            BpelObject bo = (BpelObject)ctor.newInstance(new Object[]{el});
            System.out.println("已经创建OBject对象，并且向下转型成为BpelObject对象："+ bo.getElement());
            return bo;
        } catch (Exception e) {
            throw new RuntimeException("内部编译BpelObject时发生错误");
        }
    }
    public Process parse(Element root,URI systemURI){
        //通过sax解析的方式解析文档的根节点，并且将根节点和uri传入创建出一个BpelObject对象
        //此时传入的element是process标签
        //向下转型为子类。需要进行RTTI
       return  (Process) createBpelObject(root,systemURI);
    }

}
