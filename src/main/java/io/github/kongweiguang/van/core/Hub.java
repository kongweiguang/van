package io.github.kongweiguang.van.core;

import java.util.function.Consumer;

/**
 * eventbus
 *
 * @param <C> 消息类型
 * @param <R> 返回类型
 * @author kongweiguang
 */
public interface Hub<C, R> {

    /**
     * 推送实体类消息
     *
     * @param c 实体类
     */
    default void push(final C c) {
        push(Action.of(c.getClass().getName(), c), null);
    }

    /**
     * 推送消息，设置回调
     *
     * @param c    消息
     * @param call 回调
     */
    default void push(final C c, final Consumer<R> call) {
        push(Action.of(c.getClass().getName(), c), call);
    }

    /**
     * 推送消息
     *
     * @param topic 主题
     * @param c     消息
     */
    default void push(final String topic, final C c) {
        push(Action.of(topic, c), null);
    }

    /**
     * 推送消息，设置回调
     *
     * @param topic 主题
     * @param c     消息
     * @param call  回调
     */
    default void push(final String topic, final C c, final Consumer<R> call) {
        push(Action.of(topic, c), call);
    }

    /**
     * 推送消息
     *
     * @param msg 消息
     */
    default void push(final Action<C, R> msg) {
        push(msg, null);
    }

    /**
     * 推送消息，设置回调
     *
     * @param msg  消息
     * @param call 回调
     */
    void push(final Action<C, R> msg, final Consumer<R> call);

    /**
     * 拉取指定的实体类型的消息
     *
     * @param clazz   实体类型
     * @param handler 处理器
     */
    default void pull(final Class<?> clazz, final Merge<Action<C, R>> handler) {
        pull(clazz.getName(), handler);
    }

    /**
     * 拉取指定的topic
     *
     * @param topic   主题
     * @param handler 处理器
     */
    void pull(final String topic, final Merge<Action<C, R>> handler);

    /**
     * 拉取类中加了{@link Pull}注解的方法，并执行
     *
     * @param obj 含有{@link Pull}注解的类实例
     */
    void pull(final Object obj);

    /**
     * 移除topic下指定名称的消费者
     *
     * @param topic 主题
     * @param name  处理器的名字
     */
    void remove(final String topic, final String name);
}
