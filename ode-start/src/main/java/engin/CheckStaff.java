package engin;

/**
 * @author zhanghangting
 * @date 2018/10/30 9:33
 */
public class CheckStaff {
    private String name;
    public CheckStaff(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void doSomething(String message){
        System.out.println(name + message);
    }
}
