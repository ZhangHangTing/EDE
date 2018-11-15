package bupt.zht.runtime.channels;

public interface PickResponse extends TimerResponse {
    void onRequestRcvd(int selectorIdx,String mexId);
}
