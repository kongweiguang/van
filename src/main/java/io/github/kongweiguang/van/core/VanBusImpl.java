package io.github.kongweiguang.van.core;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

import static java.util.Optional.ofNullable;

/**
 * VanEventBusImpl
 *
 * @param <C> 消息类型
 * @param <R> 返回类型
 * @author kongweiguang
 */
public final class VanBusImpl<C, R> implements VanBus<C, R> {
    private final Map<String, List<Handler<C, R>>> router = new HashMap<>();

    @Override
    public void push(final Msg<C, R> msg, final Consumer<R> call) {
        msg.callback(call);

        ofNullable(router.get(msg.topic())).ifPresent(hs -> hs.forEach(h -> {
            try {
                h.handle(msg);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }));

    }

    @Override
    public synchronized void pull(final String topic, final Handler<C, R> handler) {
        router.computeIfAbsent(topic, k -> new ArrayList<>()).add(handler);
    }

    @Override
    public synchronized void remove(final String topic, final String name) {
        ofNullable(router.get(topic)).ifPresent(h -> h.removeIf(e -> Objects.equals(e.name(), name)));
    }

    @Override
    public void pull(final Object obj) {
        for (Class<?> c = obj.getClass(); c != null; c = c.getSuperclass()) {

            for (Method m : c.getDeclaredMethods()) {
                Pull sub = m.getAnnotation(Pull.class);

                if (sub != null) {

                    Class<?>[] params = m.getParameterTypes();
                    if (params.length > 1) {
                        throw new IllegalArgumentException("method params not be > 1");
                    }

                    m.setAccessible(true);
                    pull(sub.value().isEmpty() ? params[0].getName() : sub.value(), hd(obj, params.length, m));
                }
            }

        }
    }

    private Handler<C, R> hd(final Object obj, final int size, final Method m) {
        return msg -> {
            final Object fr;

            if (size == 1) {
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
