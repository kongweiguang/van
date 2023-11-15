package io.github.kongweiguang.van.core;

import java.util.StringJoiner;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;

/**
 * 消息
 *
 * @param <C> 消息的内容
 * @param <R> 消息返回的结果
 */
public class Msg<C, R> {
    private final long id;
    private final String topic;
    private final C content;
    private Consumer<R> call;

    public Msg(final long id, final String topic, final C content) {
        this.id = id;
        this.topic = topic;
        this.content = content;
    }

    public long id() {
        return id;
    }

    public String topic() {
        return topic;
    }

    public C content() {
        return content;
    }

    public void reply(R r) {
        if (nonNull(call)) {
            call.accept(r);
        }
    }

    void callback(final Consumer<R> call) {
        this.call = call;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Msg.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("topic='" + topic + "'")
                .add("content=" + content)
                .add("call=" + call)
                .toString();
    }
}
