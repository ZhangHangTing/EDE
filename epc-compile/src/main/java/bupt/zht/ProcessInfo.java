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
    // 一个流程下面的所有事件主题,用于Monitor对这些主题进行订阅
    public static List<String> eventThemeList = new ArrayList<>();
    public static Map<String, ProcessModel> processModelMap = new HashMap<>();
    public static Map<String, ProcessInstance> processInstanceMap = new HashMap<>();
    // 一个流程的编译器
    public static EpcCompiler epcCompiler;
}
