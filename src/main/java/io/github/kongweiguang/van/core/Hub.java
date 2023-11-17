package io.github.kongweiguang.van.core;

import java.util.function.Consumer;

/**
 * hub
 *
 * @param <C> 操作类型
 * @param <R> 返回类型
 * @author kongweiguang
 */
public interface Hub<C, R> {

    /**
     * 推送实体类操作
     *
     * @param c 内容
     */
    default void push(final C c) {
        push(Action.of(c.getClass().getName(), c), null);
    }

    /**
     * 推送实体操作，设置回调
     *
     * @param c    内容
     * @param call 回调
     */
    default void push(final C c, final Consumer<R> call) {
        push(Action.of(c.getClass().getName(), c), call);
    }

    /**
     * 推送操作
     *
     * @param branch 分支
     * @param c      内容
     */
    default void push(final String branch, final C c) {
        push(Action.of(branch, c), null);
    }

    /**
     * 推送操作，设置回调
     *
     * @param branch 分支
     * @param c      内容
     * @param call   回调
     */
    default void push(final String branch, final C c, final Consumer<R> call) {
        push(Action.of(branch, c), call);
    }

    /**
     * 推送操作
     *
     * @param action 操作
     */
    default void push(final Action<C, R> action) {
        push(action, null);
    }

    /**
     * 推送操作，设置回调
     *
     * @param action 操作
     * @param call   回调
     */
    void push(final Action<C, R> action, final Consumer<R> call);

    /**
     * 拉取指定的实体类型的操作
     *
     * @param clazz 实体类型
     * @param merge 合并器
     */
    default void pull(final Class<?> clazz, final Merge<Action<C, R>> merge) {
        pull(clazz.getName(), merge);
    }

    /**
     * 拉取指定的branch
     *
     * @param branch 分支
     * @param merge  合并器
     */
    default void pull(final String branch, final Merge<Action<C, R>> merge) {
        pull(branch, 0, merge);
    }

    /**
     * 拉取指定的branch
     *
     * @param branch 分支
     * @param index  拉取的顺序
     * @param merge  合并器
     */
    void pull(final String branch, final int index, final Merge<Action<C, R>> merge);

    /**
     * 拉取类中加了{@link Pull}注解的方法，并在推送到指定的分支执行
     *
     * @param obj 含有{@link Pull}注解的类实例
     */
    void pull(final Object obj);

    /**
     * 移除branch下指定名称的合并器
     *
     * @param branch 分支
     * @param name   合并器的名字
     */
    void remove(final String branch, final String name);
}
