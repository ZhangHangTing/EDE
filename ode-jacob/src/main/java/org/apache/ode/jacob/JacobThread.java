/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.ode.jacob;

import java.lang.reflect.Method;

/**
 * 暴露JACOB 操作的类
 * Class exposing the JACOB operations.
 * <p>
 * 这些操作表示了流程代数子集中的java调用
 * Note: these operations represent a subset of a process algebra mapped into
 * Java invocations;
 * 其他层面的语法在java中被很自然的表示
 * other aspects of the syntax are represented natively in Java.
 * 并行组件作为序列化的java调用被模块化
 * In particular, parallel composition is modelled as sequential Java invocation
 * if-ele被模块化通过使用java的if-elel状态
 * and if-else are modelled using Java's if-else statement. Note also that the
 * 通道名称的作用域规则是简单的java对象可见性规则
 * scoping rules for channel names are simply the Java object visibility rules.
 */
public interface JacobThread {
    public Object getExtension(Class extensionClass);

    public String exportChannel(Channel channel);

    public Channel importChannel(String channelId, Class channelClass);

    /**
     * 创建一个流程实例，也就是一个抽象流程的具体形式
     * Create a process instance i.e. a concretion of a process abstraction.
     */
    public void instance(JacobRunnable concretion);

    /**
     *发送一条message（对象调用），这个方法不应该真的被用于对象，更应该作为代理使用
     * Send a message (object invocation). This method shouldn't really be used
     * as {@link Channel} objects may be used as proxies in this respect.
     *
     * @param channel
     *            channel on which to send the message
     * @param method
     *            method to apply
     * @param args
     *            arguments
     */
    public Channel message(Channel channel, Method method, Object[] args);

    /**
     * Create a new channel.
     */
    public Channel newChannel(Class channelType, String creator,
                              String description);

    /**
     * <p>
     *     接受通道的message，允许可能的响应
     * Receive a message on a channel, allowing for possible replication. The
     * 这个方法的作用就是为一个通道的message注册一个监听器（在JACOB框架中，Method Lists即通道监听器）
     * effect of this method is to register a listener (the method list) for a
     * 来消费一个或一定数量的通道中的message
     * message on the channel to consume either one or an infinate number of
     * 取决于replication参数的值
     * messages on the channel (depending on the value of the
     * <code>replicate</code> argument.
     * </p>
     *
     * <p>
     *     这里是一个例子，就流程术语层面而言，java表达式与下面的进程代数中的操作对应
     * With respect to process terms, the Java expression <code>object(false, x,
     * ChannelListener)</code>
     * corresponds to the process term <code> x ? { ChannelListener }</code>;
     * if in the same expression the initial <code>replicate</code> parameter
     * were instead set to <code>true</code>, corresponding term would be
     * <code> ! x ? { ChannelListener }</code>.
     * </p>
     *
     * @param replicate
     *            if set the a replication operator is present
     * @param methodList
     *            object representation of the method list
     * @throws IllegalArgumentException
     *             if the method list does not match the channel kind
     */
    public void object(boolean replicate, ChannelListener methodList)
            throws IllegalArgumentException;

    public void object(boolean reaplicate, ChannelListener[] methodLists)
            throws IllegalArgumentException;
}
