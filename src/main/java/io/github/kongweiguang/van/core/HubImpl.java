package io.github.kongweiguang.van.core;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

import static io.github.kongweiguang.van.core.Util.isTure;
import static io.github.kongweiguang.van.core.Util.notNull;
import static java.util.Optional.ofNullable;

/**
 * hubImpl
 *
 * @param <C> 内容类型
 * @param <R> 返回类型
 * @author kongweiguang
 */
public final class HubImpl<C, R> implements Hub<C, R> {
    private final Map<String, List<Merge<Action<C, R>>>> repo = new HashMap<>();

    @Override
    public void push(final Action<C, R> action, final Consumer<R> call) {
        notNull(action, "action must not be null");

        action.callback(call);

        ofNullable(repo.get(action.branch())).ifPresent(ms -> ms.forEach(m -> {
            try {
                m.merge(action);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }));

    }

    @Override
    public synchronized void pull(final String branch, final Merge<Action<C, R>> merge) {
        notNull(branch, "branch must not be null");
        notNull(merge, "merge must not be null");


        repo.computeIfAbsent(branch, k -> new ArrayList<>()).add(merge);
    }

    @Override
    public synchronized void remove(final String branch, final String name) {
        notNull(branch, "branch must not be null");
        notNull(name, "name must not be null");

        ofNullable(repo.get(branch)).ifPresent(ms -> ms.removeIf(m -> Objects.equals(m.name(), name)));
    }

    @Override
    public void pull(final Object obj) {
        notNull(obj, "obj must not be null");

        for (Class<?> c = obj.getClass(); c != null; c = c.getSuperclass()) {

            for (Method m : c.getDeclaredMethods()) {
                Pull sub = m.getAnnotation(Pull.class);

                if (sub != null) {

                    Class<?>[] params = m.getParameterTypes();

                    isTure(params.length <= 1, "method params not be > 1");

                    m.setAccessible(true);
                    pull(sub.value().isEmpty() ? params[0].getName() : sub.value(), mr(obj, params.length, m));
                }
            }

        }
    }

    private Merge<Action<C, R>> mr(final Object obj, final int size, final Method m) {
        return action -> {
            final Object fr;

            if (size == 1) {
                fr = m.invoke(obj, action.content());
            } else {
                fr = m.invoke(obj);
            }

            if (void.class.isAssignableFrom(m.getReturnType()) || Void.class.isAssignableFrom(m.getReturnType())) {
                return;
            }

            action.res((R) fr);
        };
    }
}
