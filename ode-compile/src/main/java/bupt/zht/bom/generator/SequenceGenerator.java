package bupt.zht.bom.generator;

import bupt.zht.bom.Activity;
import bupt.zht.bom.activity.CompositeActivity;
import bupt.zht.bom.activity.SequenceActivity;
import bupt.zht.o.OActivity;
import bupt.zht.o.OSequence;

public class SequenceGenerator extends DefaultActivityGenerator {
    public void compile(OActivity output, Activity src) {
        //尝试向下转型（利用RTTI机制，运行是类型检查），如果是这个基类的子类的话，那么代码正确执行
        //如果尝试向下转型失败，抛出异常
        OSequence oseq = (OSequence) output;
        //向上转型，从一个子类传入到基类，调用基类的方法没毛病（因为子类本质上也是一个基类，拥有基类的所有方法）
        compileChildren(oseq,(SequenceActivity)src);
    }

    protected void compileChildren(OSequence dest, CompositeActivity src) {
        for(Activity child : src.getActivitys()){
            OActivity compiledChild = _context.compile(child);
            dest.sequence.add(compiledChild);
        }
    }

    public OActivity newInstance(Activity src) {
        return new OSequence(_context.getOProcess(),_context.getCurrent());
    }
}
