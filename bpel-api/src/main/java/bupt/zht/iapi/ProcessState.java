package bupt.zht.iapi;
//用户可选择的在配置存储中流程是状态
public enum ProcessState {
    //Process能够创建新的实例和执行旧的实例
    ACTIVE,
    //Process能够执行旧的实例，但是不能创建新的实例
    RETIRED,
    //Process不能创建新的实例，也不能执行旧的实例
    DISABLED
}
