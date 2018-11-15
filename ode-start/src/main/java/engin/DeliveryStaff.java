package engin;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * @author zhanghangting
 * @date 2018/10/21 21:00
 */
public class DeliveryStaff {
    private String name;
    private String permission;
    public DeliveryStaff(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
    public void doSomething(String message){
        System.out.println(name + message);
    }
}