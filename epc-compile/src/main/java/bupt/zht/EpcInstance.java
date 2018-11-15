package bupt.zht;

import bupt.zht.activity.Event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanghangting
 * @date 2018/10/15 9:45
 */
public class EpcInstance {

    private String uuid;
    private String state;
    // 该实例对应的模型
    private EpcModel epcModel;

    public EpcInstance(EpcModel epcModel){
        this.epcModel = epcModel;
    }
    public String getEpcInstanceUuid(){
        return uuid;
    }
    public EpcModel getEpcModel(){
        return epcModel;
    }
}
