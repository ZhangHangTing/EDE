package bupt.zht.bom;

import bupt.zht.bom.activity.*;
import bupt.zht.bom.generator.*;
public class BpelCompiler20 extends BpelCompiler {
    public BpelCompiler20(){
        //初始化编译器的时候，将bpel对象的类信息，和生成bpel对象类生成器注册到编译器中
        //每一个bpel对象的类生成器继承了DefaultActivityGenerator，DefaultActivityGenerator实现了ActivityGenerator的三个接口
        //设置应用上下文、根据Bpel对象编译OBpel对象，以及返回一个实例化一个OBpel对象
        //在DefaultActivityGenerator中添加了对拓展元素和拓展元素处理失效的操作
        registerActivityCompiler(AssignActivity.class,new AssignGenerator());
        registerActivityCompiler(SequenceActivity.class,new SequenceGenerator());
        registerActivityCompiler(ReplyActivity.class,new ReplyGenerator());
        registerActivityCompiler(ReceiveActivity.class,new ReceiveGenerator());
        registerActivityCompiler(EmptyActivity.class,new EmptyGenerator());
    }
}
