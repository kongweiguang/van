package io.github.kongweiguang.van.core;

import java.lang.reflect.Method;
import java.util.*;

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
    public void push(final Msg<C, R> msg, final java.util.function.Consumer<R> call) {
        msg.callback(call);

        router.computeIfAbsent(msg.topic(), k -> new ArrayList<>()).forEach(h -> {
            try {
                h.handle(msg);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Override
    public synchronized void consumer(final String topic, final Handler<C, R> handler) {
        router.computeIfAbsent(topic, k -> new ArrayList<>()).add(handler);
    }

    @Override
    public void remove(final String topic, final String name) {
        router.computeIfAbsent(topic, k -> new ArrayList<>()).removeIf(e -> Objects.equals(e.name(), name));
    }

    @Override
    public void register(final Object obj) {
        for (Class<?> c = obj.getClass(); c != null; c = c.getSuperclass()) {

            for (Method m : c.getDeclaredMethods()) {
                Consumer sub = m.getAnnotation(Consumer.class);

                if (sub != null) {

                    Class<?>[] params = m.getParameterTypes();
                    if (params.length > 1) {
                        throw new IllegalArgumentException("method params not be > 1");
                    }

                    m.setAccessible(true);
                    consumer(sub.value().isEmpty() ? params[0].getName() : sub.value(), hd(obj, params, m));
                }
            }

        }
    }

    private Handler<C, R> hd(final Object obj, final Class<?>[] params, final Method m) {
        return msg -> {
            final Object fr;

            if (params.length == 1) {
                fr = m.invoke(obj, msg.content());
            } else {
                fr = m.invoke(obj);
            }

            if (void.class.isAssignableFrom(m.getReturnType()) || Void.class.isAssignableFrom(m.getReturnType())) {
                return;
            }

            msg.reply((R) fr);
        };
    }
}
