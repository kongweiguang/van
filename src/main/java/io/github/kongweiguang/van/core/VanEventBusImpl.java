package io.github.kongweiguang.van.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * VanEventBusImpl
 *
 * @param <C>
 * @param <R>
 * @author kongweiguang
 */
public class VanEventBusImpl<C, R> implements VanEventBus<C, R> {
    private final Map<String, List<Handler<C, R>>> router = new HashMap<>();

    @Override
    public void push(final Msg<C, R> msg, final Consumer<R> call) {
        msg.callback(call);
        router.computeIfAbsent(msg.topic(), k -> new ArrayList<>()).forEach(e -> e.handle(msg));
    }

    @Override
    public synchronized void consumer(final String topic, final Handler<C, R> handler) {
        router.computeIfAbsent(topic, k -> new ArrayList<>()).add(handler);
    }

    @Override
    public void remove(final String topic) {
        router.remove(topic);
    }
}
