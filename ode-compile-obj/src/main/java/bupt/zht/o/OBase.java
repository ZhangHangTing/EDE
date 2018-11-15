package bupt.zht.o;


import java.io.Serializable;

public class OBase implements Serializable{
    static final long serialVersionUID = -1L;
    private final int _id;
    private final OProcess _owner;
    public DebugInfo debugInfo;
    public OBase(OProcess owner){
        _owner = owner;
        if(owner == null){
            _id = 0;
        }else{
            _id = ++_owner._childIdCounter;
            _owner._children.add(this);
        }
    }
    public OProcess getOwner(){
        return (OProcess)(_owner == null ? this : _owner);
    }
    public int getId(){
        return _id;
    }
    public boolean euqals(Object obj){
        if(! (obj instanceof OBase))
            return false;
        OBase other = (OBase)obj;
        return (_id == 0 && other._id == 0) || (_id == other._id && other._owner.equals(_owner));
    }
    public int hashCode(){
        return _id;
    }
    //让BPEL标签活动对应的类信息脱水
    public void dehydrate(){
        if(debugInfo != null){
            debugInfo.description = null;
            debugInfo = null;
        }
    }

}
