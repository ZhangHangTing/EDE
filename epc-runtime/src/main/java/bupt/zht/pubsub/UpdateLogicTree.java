package bupt.zht.pubsub;

import bupt.zht.EpcObject;
import bupt.zht.ProcessInfo;
import bupt.zht.activity.*;
import bupt.zht.service.Service;
import bupt.zht.service.ServiceFactory;

import java.util.Map;
import java.util.Queue;

/**
 * @author zhanghangting
 * @date 2018/11/27 16:52
 */
public class UpdateLogicTree {

    private Map<LogicUnit, Queue<EpcObject>> logicUnitObjectMap;

    public UpdateLogicTree(){
        logicUnitObjectMap = ProcessInfo.epcCompiler.getLogicUnitObjectMap();
    }

    public void updateFunctionTree(LogicTreeNode root, Event event, Function function, String message) {
        EpcObject rootObject = root.getEpcObject();
        // 最多需要循环每一个逻辑节点的状态变化
        int loopNum = logicUnitObjectMap.size();
        for(int i = 0; i < loopNum; i++){
            for(Map.Entry<LogicUnit,Queue<EpcObject>> map : logicUnitObjectMap.entrySet()){
                LogicUnit logicUnit = map.getKey();
                Queue<EpcObject> epcObjects = map.getValue();
                // 找到队列中相同的事件节点，才需要更新逻辑节点，更新了逻辑节点，才需要更新其他的节点
                if(epcObjects.contains(event)){
                    System.out.println("找到了逻辑节点对应的触发事件节点：" + event.getName());
                    if(logicUnit instanceof And){
                        if(((And) logicUnit).isFirstComing()){
                            System.out.println("And节点的第二个事件到达！");
                            ((And) logicUnit).setNextComing(true);
                        } else{
                            System.out.println("And节点的第一个事件到达！");
                            ((And) logicUnit).setFirstComing(true);
                        }
                    }else{
                        logicUnit.setAlive(true);
                    }
                }
            }
        }
        // 更新完Map中所有的逻辑节点状态以后，只需要将所有函数的root节点和该Map中的逻辑节点相匹配即可
        for(Map.Entry<LogicUnit,Queue<EpcObject>> map : logicUnitObjectMap.entrySet()){
            LogicUnit logicUnit = map.getKey();
            if(logicUnit.equals(rootObject) && logicUnit.isAlive()){
                executeFunction(function,message);
            }
        }
        System.out.println("消息到达，更新逻辑事件表达树");
    }
    // 如何执行该函数是关键
    public void executeFunction(Function function, String message){
        Service executeService = ServiceFactory.getServiceInstance(function.getServiceName(),message,function.getName());
        executeService.run();
    }
}
