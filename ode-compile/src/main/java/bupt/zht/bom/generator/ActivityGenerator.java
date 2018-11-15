package bupt.zht.bom.generator;

import bupt.zht.bom.Activity;
import bupt.zht.bom.CompilerContext;
import bupt.zht.o.OActivity;

public interface ActivityGenerator {
    public void setContext(CompilerContext context);
    public void compile(OActivity output, Activity src);
    public OActivity newInstance(Activity src);
}
