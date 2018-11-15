package bupt.zht.iapi;

import org.dom4j.QName;

public interface BpelEngine extends Scheduler.JobProcessor {
    MyRoleMessageExchange createMessageExchange(String clientKey, QName serviceId, String operation);
    /**
     * Retrieve a message identified by the given identifer.
     *
     * @param mexId
     *            message exhcange identifier
     * @return associated message exchange
     */
    MessageExchange getMessageExchange(String mexId);

    int getProcessThrottledMaximumCount();

    long getProcessThrottledMaximumSize();

    int getHydratedProcessCount(QName processName);

    long getHydratedProcessSize(QName processName);

    boolean dehydrateLastUnusedProcess();
}
