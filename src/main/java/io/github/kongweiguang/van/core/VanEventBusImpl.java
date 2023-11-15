package io.github.kongweiguang.van.core;

import java.util.function.Consumer;

public class VanEventBusImpl<C, R> implements VanEventBus<C, R> {
    private final Router<C, R> router = new Router<>();

    @Override
    public void push(final Msg<C, R> msg, final Consumer<R> call) {
        msg.callback(call);
        router.get(msg.topic()).forEach(e -> e.handle(msg));
    }

    @Override
    public void consumer(final Object topic, final Handler<C, R> handler) {
        router.add(topic, handler);
    }
}
