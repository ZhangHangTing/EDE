package bupt.zht.bom.generator;

import bupt.zht.bom.CompilerContext;

public abstract class DefaultActivityGenerator implements ActivityGenerator{
    protected CompilerContext _context;
    public void setContext(CompilerContext context){
        _context = context;
    }
    //默认拓展元素
    //拓展元素失效处理机制
}
