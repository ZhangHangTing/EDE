package org.apache.ode.jacob.test;

import org.apache.ode.jacob.SynchChannel;
//第一步，定义一个Test接口
public interface Test {
    public SynchChannel print(String str);
}
