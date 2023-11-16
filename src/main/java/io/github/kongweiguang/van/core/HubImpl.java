package io.github.kongweiguang.van.core;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

import static io.github.kongweiguang.van.core.Util.isTure;
import static io.github.kongweiguang.van.core.Util.notNull;
import static java.util.Optional.ofNullable;

/**
 * VanEventBusImpl
 *
 * @param <C> 消息类型
 * @param <R> 返回类型
 * @author kongweiguang
 */
public final class HubImpl<C, R> implements Hub<C, R> {
    private final Map<String, List<Merge<Action<C, R>>>> router = new HashMap<>();

    @Override
    public void push(final Action<C, R> msg, final Consumer<R> call) {
        notNull(msg, "msg must not be null");

        msg.callback(call);

        ofNullable(router.get(msg.topic())).ifPresent(hs -> hs.forEach(h -> {
            try {
                h.merge(msg);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }));

    }

    @Override
    public synchronized void pull(final String topic, final Merge<Action<C, R>> handler) {
        notNull(topic, "topic must not be null");
        notNull(handler, "handler must not be null");


        router.computeIfAbsent(topic, k -> new ArrayList<>()).add(handler);
    }

    @Override
    public synchronized void remove(final String topic, final String name) {
        notNull(topic, "topic must not be null");
        notNull(name, "name must not be null");

        ofNullable(router.get(topic)).ifPresent(h -> h.removeIf(e -> Objects.equals(e.name(), name)));
    }

    @Override
    public void pull(final Object obj) {
        notNull(obj, "obj must not be null");

        for (Class<?> c = obj.getClass(); c != null; c = c.getSuperclass()) {

            for (Method m : c.getDeclaredMethods()) {
                Pull sub = m.getAnnotation(Pull.class);

                if (sub != null) {

                    Class<?>[] params = m.getParameterTypes();

                    isTure(params.length < 1, "method params not be > 1");

                    m.setAccessible(true);
                    pull(sub.value().isEmpty() ? params[0].getName() : sub.value(), hd(obj, params.length, m));
                }
            }

        }
    }

    private Merge<Action<C, R>> hd(final Object obj, final int size, final Method m) {
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
