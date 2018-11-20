package engin;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanghangting
 * @date 2018/10/21 21:16
 */
public class ProcessManagement {
    // private List<EpcInstance> instanceList = new ArrayList<>();
   // private EpcModel myModel;
    // 解析epml文档，将EPC流程解析到数据库中
    public void parseEmpl(String epmlFIlePath){
    }
    public void createProcessInstance(){
        // 监听每一个流程模型的起始事件，当起始事件到达的时候，就创建一个流程实例
//        while(true){
//            if(MonitorService.getEventName().equals("shoppingarrive")){
//                // 创建一个流程实例，并且将该流程实例放入引擎实例列表进行管理
//                EpcInstance shoppingProcessInstance = new EpcInstance(myModel);
//                executeProcessInstance(shoppingProcessInstance);
//            }else if(MonitorService.getEventName().equals("shoppingpay")){
//                // 交付货物事件主题达到，执行交付货物
//            }else if(MonitorService.getEventName().equals("shoppingblock")){
//                // 阻塞货物事件主题到达，执行阻塞货物操作
//            }else if(MonitorService.getEventName().equals("shoppingreject")){
//                // 拒绝货物事件主题到达，执行阻塞货物操作
//            }
//        }
    }
    // public void executeProcessInstance(EpcInstance epcInstance){
//        while(!epcInstance.getEpcModel().getNodeQueue().isEmpty()){
//        }
        // 如果流程实例在队列中还有执行节点，则表明该流程实例并没有执行完，从队列中取出第一个活动节点执行
//        if(!epcInstance.getEpcModel().getNodeQueue().isEmpty()){
//            EpcObject activityObject = epcInstance.getEpcModel().getNodeQueue().peek();
//            if(activityObject instanceof Event){
//                ((Event) activityObject).transferToNextActivity();
//            }else if (activityObject instanceof Function){
//
//            }else if(activityObject instanceof LogicUnit){
//
//            }
//            // 这里取出相关活动节点之后，将节点从队列中移除
//        }
//        else{
//            // 流程实例执行完成
//        }
   // }
}
