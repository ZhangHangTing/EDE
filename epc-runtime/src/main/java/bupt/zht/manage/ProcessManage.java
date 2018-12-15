package bupt.zht.manage;

import bupt.zht.process.ProcessInstance;
import bupt.zht.process.ProcessModel;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author zhanghangting
 * @date 2018/12/15 17:23
 */
    // 这里通过解析事件传递过来的sechme文件，来确定该消息事件属于哪一个流程实例，或者是需要创建一个流程实例
    // 在流程管理类中，一个流程模型管理管理多个流程实例，需要在这里对这些数据进行管理，
    // 能够提供外部的接口对这些流程实例进行增删改查
public class ProcessManage {

    private Map<ProcessModel,List<ProcessInstance>> processModelListMap;

    // 当事件到达，对应的schema消息到达的时候，进行解析,找到这个事件对应的流程模型和流程实例
    public void parseSchema(String schemaFile) throws DocumentException {

        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(schemaFile));
        Element processNode = document.getRootElement();

        // 获取流程模型和流程实例
        Element model = processNode.element("model");
        String modelMark = model.getText();
        Element instance = processNode.element("instance");
        String instanceMark = instance.getText();
        // 获取事件消息
        // todo
    }
    // 这里需要添加判断，什么时候创建流程实例，什么时候更新流程实例
}
