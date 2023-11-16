package io.github.kongweiguang.van.core;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Consumer;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * 消息
 *
 * @param <C> 消息的内容
 * @param <R> 消息返回的结果
 * @author kongweiguang
 */
public final class Action<C, R> {
    private final long id;
    private final String topic;
    private final C content;
    private Consumer<R> call;
    private Map<Object, Object> attr;

    private Action(final long id, final String topic, final C content) {
        this.id = id;
        this.topic = topic;
        this.content = content;
    }

    public static <C, R> Action<C, R> of(final String topic, final C c) {
        return of(IdGen.of.next(), topic, c);
    }

    public static <C, R> Action<C, R> of(final long id, final String topic, final C c) {
        return new Action<>(id, topic, c);
    }

    /**
     * 获取消息id
     *
     * @return id
     */
    public long id() {
        return id;
    }

    /***
     * 获取消息的主题
     *
     * @return 主题
     */
    public String topic() {
        return topic;
    }

    /**
     * 消息内容
     *
     * @return 消息内容
     */
    public C content() {
        return content;
    }

    /**
     * 判断是否有回调
     *
     * @return 是否有回调
     */
    public boolean hasCallBack() {
        return nonNull(call);
    }

    /**
     * 触发调用者的回调
     *
     * @param r 回调参数
     */
    public void reply(R r) {
        if (hasCallBack()) {
            call.accept(r);
        }
    }

    /**
     * 获取携带参数
     *
     * @param k   键
     * @param <T> 返回类型
     * @return 值
     */
    public <T> T attr(final String k) {
        if (isNull(attr)) {
            return null;
        }

        return (T) attr.get(k);
    }

    /**
     * 添加参数
     *
     * @param k 键
     * @param v 值
     * @return this
     */
    public Action<C, R> attr(final Object k, final Object v) {
        if (isNull(attr)) {
            this.attr = new HashMap<>();
        }

        attr.put(k, v);
        return this;
    }

    /**
     * 设置回调函数
     *
     * @param call 回调函数
     */
    void callback(final Consumer<R> call) {
        this.call = call;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Action.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("topic='" + topic + "'")
                .add("content=" + content)
                .add("attr=" + attr)
                .toString();
    }
}
