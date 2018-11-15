package bupt.zht.bom;
import bupt.zht.o.OPartnerLink;
import bupt.zht.o.OProcess;
import bupt.zht.o.Serializer;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.*;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class BpelC {
    private File _bpelFile;
    private File _suDir;
    private Element _root;
    public OutputStream _outputStream = null;
    //private ResourceFinder rs;
    private URI _bpel11wsdl;
    private Map<String,Object> _compileProperties;
    private ProcessInfo processInfo = null;
    private BpelC(){}
    public static BpelC newBpelCompiler(){
        return new BpelC();
    }
    public void setBaseDirectory(File baseDir){
        _suDir = baseDir;
    }
    //然后将一个process对象转换成OProcess对象
    public void compile(Process process, String cbpPath,ProcessInfo pinfo){
        //初始化编译器，初始化编译器的时候将bpel对象的类信息，以及对应类的生成器注册到编译器中
        BpelCompiler compiler = new BpelCompiler20();
        //ResourceFinder wf;
        //以O为首字母的bpel标签表示序列化之后的bpel对象
        OProcess oprocess;
        oprocess = compiler.compile(process);
        Serializer fileHeader = new Serializer(System.currentTimeMillis());
        //将序列化之后的oprocess保存到输出路径中
        try {
            _outputStream = new BufferedOutputStream(new FileOutputStream(cbpPath));
            fileHeader.writeOProcess(oprocess,_outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("序列化成功");
    }
    //首先将一个bpel文件转换成一个process对象
    public void compile(File bpelFile,String cbpPath){
        _bpelFile = bpelFile;
        //对于process对象来说，继承于作用域对象scope，作用域对象scope继承于所有bpel文件对象基类BpelBoject
        Process process;
        //通过工厂方式创建process对象
        process = BpelObjectFactory.getInstance().parse( _root,_bpelFile.toURI());
        System.out.println("测试生成的process对象：" + process.getName());
        //compile(process,cbpPath);
    }
    public void parseBpel(File bpelFile){
        SAXReader saxReader = new SAXReader();
        try {
            Document doc = saxReader.read(bpelFile);
            _root = doc.getRootElement();
            //最后process对象是根据_root的接口信息创建出来的，
            //所以在这里需要将整个bpel xml文档的节点结构化到该接口信息中去，比如root元素下面的孩子节点，属性，链接关系等等
            //下午需要在这里解析整个process文档树
            parseNode(_root);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    public void parseNode(Element ele){
        System.out.println(ele.getName() + ":" + ele.getText().trim());
        List<Attribute> attr = ele.attributes();
        for(Attribute a : attr){
            //getName表示属性名称，getValue表示属性值
            System.out.println(a.getName() + ":" + a.getValue());
        }
        System.out.println();
        List<Element> eleList = ele.elements();
        for(Element e : eleList){
            parseNode(e);
        }
    }
}
