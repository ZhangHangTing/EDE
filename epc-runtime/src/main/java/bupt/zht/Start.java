package bupt.zht;

import bupt.zht.monitor.Monitor;
import org.dom4j.DocumentException;

/**
 * @author zhanghangting
 * @date 2018/11/27 16:32
 */
public class Start {
    public static void main(String[] args) throws DocumentException {
        EpcCompiler epcCompiler = new EpcCompiler();
        String path = System.getProperty("user.dir") + "/epc-compile/src/main/resources/";
        epcCompiler.compile(path + "ProductAndAssemble.xml");
        epcCompiler.mappingLogicEvent();
        epcCompiler.showLogicUnitEventsMap();
        epcCompiler.extract();
        epcCompiler.showFunctionLogicTree();
        epcCompiler.showEventFunctionMap();
        ProcessInfo.epcCompiler = epcCompiler;
        Monitor.start();
//        while (true) {
//            System.out.println("running");
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
