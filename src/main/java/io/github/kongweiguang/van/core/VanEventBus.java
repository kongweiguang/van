package io.github.kongweiguang.van.core;

import java.util.function.Consumer;

public interface VanEventBus<C, R> {
    default void push(final Msg<C, R> msg) {
        push(msg, null);
    }

    default void push(final C c) {
        push(MsgFactory.of(c.getClass().getName(), c), null);
    }

    default void push(final C c, final Consumer<R> call) {
        push(MsgFactory.of(c.getClass().getName(), c), call);
    }

    void push(final Msg<C, R> msg, final Consumer<R> call);

    void consumer(final Object topic, final Handler<C, R> handler);

    default void consumer(final Class clazz, final Handler<C, R> handler) {
        consumer(clazz.getName(), handler);
    }

}
