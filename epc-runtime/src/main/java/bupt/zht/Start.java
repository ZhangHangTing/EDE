package bupt.zht;

import bupt.zht.monitor.Monitor;
import bupt.zht.process.ProcessInstance;
import bupt.zht.process.ProcessModel;
import org.dom4j.DocumentException;

/**
 * @author zhanghangting
 * @date 2018/11/27 16:32
 */
public class Start {
    public static void main(String[] args) throws DocumentException {
        EpcCompiler epcCompiler = new EpcCompiler();
        String path = System.getProperty("user.dir") + "/epc-compile/src/main/resources/";
        epcCompiler.compile(epcCompiler,path + "ProductAndAssemble.xml");
        System.out.print("EPML流程模型已经解析完毕，输出相关信息：");
        int i = 1;
        for(ProcessModel processModel : ProcessInfo.processModelList){
            System.out.println("第 " + i + "个流程模型：" + processModel.getProcessModelId());
            System.out.println(processModel);
        }
        System.out.println("接下来监听流程实例事件信息..................");
        Monitor.start();
        while (true) {
//            for(ProcessModel processModel : ProcessInfo.processModelList){
//                System.out.println("打印当前" + processModel.getProcessModelId() + "流程模型中的流程实例：");
//                for(ProcessInstance processInstance : processModel.getProcessInstancesList()){
//                    System.out.println(processInstance.getProcessInstanceId());
//                }
//            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
