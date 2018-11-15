package bupt.zht.evt;

public class ActivityEvent extends ScopeEvent {
    private String activityName;
    private String activityType;
    private int declarationId;
    private long aid;
    public ActivityEvent(){
        super();
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public void setActivityDeclarationId(int declarationId) {
        this.declarationId = declarationId;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public String getActivityType() {
        return activityType;
    }

    public int getActivityDeclarationId() {
        return declarationId;
    }

    public long getAid() {
        return aid;
    }


    public TYPE getType(){
        return TYPE.activityLifecycle;
    }
}
