package bupt.zht.service;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

public class AssembleService implements Service{
    private String tool;
    private String assembleSatff;
    public AssembleService(String tool, String assembleSatff){
        this.tool = tool;
        this.assembleSatff = assembleSatff;
    }
    @Override
    public void run() {
        System.out.print(assembleSatff + " 执行： " + tool);
    }
}
