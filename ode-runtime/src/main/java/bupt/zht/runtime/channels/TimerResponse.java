package bupt.zht.runtime.channels;

public interface TimerResponse {
    void onTimeout();
    void onCancel();
}
