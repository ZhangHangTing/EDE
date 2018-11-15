package org.apache.ode.utils;

import javafx.beans.binding.ObjectExpression;

import java.util.*;

//一个基于键值对的缓存区，内化不可变的值，可以通过这个缓存区共享不同的BPEL对象
public class InterPool {
    //我这边用hashMap代替，ode中他是通过实现Map和序列化的方式自己定义了一个Map
    private static Map<String,String> cacheValues = new HashMap<String, String>();
    private static Set<String> cacheBlocks = Collections.synchronizedSet(new HashSet<String>());
    protected InterPool(){}
    public static void runBlock(InternableBlock block){
        String processId = getProcessId();
        cacheBlocks.add(processId);
        cacheBlocks.remove(processId);
        clearAll(processId);
    }
    public static Object intern(Object key,Object value){
        String processId = getProcessId();
        if(!cacheBlocks.contains(processId)){
            return value;
        }
//        synchronized (cacheValues){
//            List values = (List)cacheValues.get(processId);
//        }
        return null;
    }
    //移除所有对应processId对应的值
    protected  static void clearAll(String processId){
        synchronized (cacheValues){
            cacheValues.remove(processId);
        }
    }
    private static String getProcessId(){
        return String.valueOf(Thread.currentThread().getId());
    }
    //客户端通过实现run方法来运行他们在缓存机制下的上下文代码块
    public interface InternableBlock{
        public void run();
    }

}
