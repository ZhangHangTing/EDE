package bupt.zht.iapi;

import org.dom4j.QName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public interface Scheduler {
    interface JobProcessor{
        void onScheduledJob(JobInfo jobInfo);
    }
    class JobInfo implements Serializable{
        public final String jobName;
        public final int retryCount;
        public final JobDetails jobDetails;
        public JobInfo(String jobName,JobDetails jobDetails,int retryCount){
            this.jobName = jobName;
            this.jobDetails = jobDetails;
            this.retryCount = retryCount;
        }
    }
    enum JobType {
        /**
         * is used for scheduled timer tasks like in pick's/evenhandler's onAlarm.
         */
        TIMER,
        /**
         * is used for resuming process instances if the time slice has been exeeded
         * or when the debugger lets the process instance resume.
         */
        RESUME,

        /**
         * is used to let the runtime process an incoming message after it has been
         * received and stored by the IL. It will try to correlate the message if
         * a route to an IMA can be found.
         */
        INVOKE_INTERNAL,

        /**
         * is used when the response from a two-way invocation comes back and shall be
         * passed to the runtime.
         */
        INVOKE_RESPONSE,

        /**
         * is used to schedule the matchmaking after adding a route to the correlator, i.e.
         * if a IMA is now waiting for a message. If the message is already in the queue,
         * this matcher job will find it.
         */
        MATCHER,

        /**
         * is used to check for failed partner invocations. It runs after a defined time
         * out, checks whether a response has arrived and if not, it marks the MEX as
         * faulted.
         */
        INVOKE_CHECK,

        /**
         * is used to avoid the race condition when a message has been correlated but
         * no process instance is able to process it and the route has been added
         * meanwhile. It just retries the correlation.
         */
        MEX_MATCHER
    }
   class JobDetails {
        public Long instanceId;
        public String mexId;
        public String processId;
        public String type;
        public String channel;
        public String correlatorId;
        public String correlationKeySet;
        public Integer retryCount;
        public Boolean inMem;
        public Map<String, Object> detailsExt = new HashMap<String, Object>();

        public Boolean getInMem() {
            return inMem == null ? false : inMem;
        }

        public void setInMem(Boolean inMem) {
            this.inMem = inMem;
        }

        public String getMexId() {
            return mexId;
        }
        public void setMexId(String mexId) {
            this.mexId = mexId;
        }
        public QName getProcessId() {
            return processId == null ? null : QName.get(processId);
        }

        public void setProcessId(QName processId) {
            this.processId = "" + processId;
        }

        public JobType getType() {
            return JobType.valueOf(type);
        }

        public void setType(JobType type) {
            this.type = type.toString();
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getCorrelatorId() {
            return correlatorId;
        }

        public void setCorrelatorId(String correlatorId) {
            this.correlatorId = correlatorId;
        }

//        public CorrelationKeySet getCorrelationKeySet() {
//            return new CorrelationKeySet(correlationKeySet);
//        }
//
//        public void setCorrelationKeySet(CorrelationKeySet correlationKeySet) {
//            this.correlationKeySet = correlationKeySet == null ? null : correlationKeySet.toCanonicalString();
//        }

        public Integer getRetryCount() {
            return retryCount == null ? 0 : retryCount;
        }

        public void setRetryCount(Integer retryCount) {
            this.retryCount = retryCount;
        }

        public Long getInstanceId() {
            return instanceId;
        }

        public void setInstanceId(Long instanceId) {
            this.instanceId = instanceId;
        }

        public Map<String, Object> getDetailsExt() {
            return detailsExt;
        }

        public void setDetailsExt(Map<String, Object> detailsExt) {
            this.detailsExt = detailsExt;
        }
    }
}
