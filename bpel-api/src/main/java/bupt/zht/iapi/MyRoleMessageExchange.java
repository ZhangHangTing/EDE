package bupt.zht.iapi;

import org.dom4j.QName;

import java.util.concurrent.Future;

public interface MyRoleMessageExchange extends MessageExchange{
    public enum CorrelationStatus {
        /** The EPR is not associated with a process. */
        UKNOWN_ENDPOINT,

        /** The request resulted in the creation of a new instance. */
        CREATE_INSTANCE,

        /** The request was matched to an existing instance.fail */
        MATCHED,

        /** The request did not match an existing instance and was queued. */
        QUEUED
    }
    CorrelationStatus getCorrelationStatus();
    Future invoke(Message request);

    /**
     * Complete the message, exchange: indicates that the client has receive the
     * response (if any).
     */
    void complete();

    /**
     * Associate a client key with this message exchange.
     *
     * @param clientKey
     */
    void setClientId(String clientKey);

    /**
     * Get the previously associated client key for this exchange.
     *
     * @return
     */
    String getClientId();

    /**
     * Get the name of the service targetted in this message exchange.
     *
     * @return service name
     */
    QName getServiceName();

    /**
     * Should be called by the external partner when it's done with the
     * message exchange. Ncessary for a better resource management and
     * proper mex cleanup.
     */
    void release(boolean instanceSucceeded);
}
