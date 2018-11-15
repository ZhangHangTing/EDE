package bupt.zht;

import bupt.zht.activity.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author zhanghangting
 * @date 2018/10/15 9:23
 */
public class EpcModel {

    private String modelId;
    // 只有一个主执行队列
    private Queue<EpcObject> mainEpcFlowQueue;
    // 有多个片段执行队列
    private List<Queue<EpcObject>> partEpcFlowQueue;

    public void ParseCompleteModel(){

    }
    // 解析EPML文件，将流程模型之间的标签关系抽取出来，保存到数据库中
    public void parseEPMLDocument(String epmlFile){

    }
//    public Event getEventById(String id){
//        for(Event e : eventList){
//            if(id.equals(e.getId()))
//                return e;
//        }
//        return null;
//    }
//    public Event getEventByName(String name){
//        for(Event e : eventList){
//            if(name.equals(e.getName()))
//                return e;
//        }
//        return null;
//    }
//    public Function getFunctionById(String id){
//        for(Function f : functionList){
//            if(id.equals(f.getId()))
//                return f;
//        }
//        return null;
//    }
//    public Function getFunctionByName(String name){
//        for(Function f : functionList){
//            if(name.equals(f.getName()))
//                return f;
//        }
//        return null;
//    }
}
