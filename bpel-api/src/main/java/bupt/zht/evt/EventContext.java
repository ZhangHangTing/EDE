package bupt.zht.evt;

public interface EventContext {
    Long getScopeInstanceId();
    String[] getVariableNames();
    String getVariableData(String varName);
}
