package bupt.zht;

import bupt.zht.process.ProcessInstance;
import bupt.zht.process.ProcessModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanghangting
 * @date 2018/11/21 15:44
 */
public class ProcessInfo {
    // 部署到系统中的所有事件主题,用于Monitor对这些主题进行订阅
    public static List<String> eventThemeList = new ArrayList<>();
    // 将系统中编译过的流程模型都保存到list中，每一个流程模型中保存该模型对应的流程实例
    public static List<ProcessModel> processModelList = new ArrayList<>();
}
