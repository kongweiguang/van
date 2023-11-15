package io.github.kongweiguang.van.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class VanEventBusImpl<C, R> implements VanEventBus<C, R> {
    private final Map<Object, List<Handler<C, R>>> router = new HashMap<>();

    @Override
    public void push(final Msg<C, R> msg, final Consumer<R> call) {
        msg.callback(call);
        router.computeIfAbsent(msg.topic(), k -> new ArrayList<>()).forEach(e -> e.handle(msg));
    }


    @Override
    public void consumer(final Object topic, final Handler<C, R> handler) {
        router.computeIfAbsent(topic, k -> new ArrayList<>()).add(handler);
    }
}
