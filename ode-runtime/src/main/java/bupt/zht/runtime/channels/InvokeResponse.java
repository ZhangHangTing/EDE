package bupt.zht.runtime.channels;

public interface InvokeResponse {
    void onResponse();
    void onFault();
    void onFailure();
}
