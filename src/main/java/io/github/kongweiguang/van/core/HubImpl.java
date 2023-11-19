package io.github.kongweiguang.van.core;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import static io.github.kongweiguang.van.core.Util.*;
import static java.util.Optional.ofNullable;

/**
 * hubImpl
 *
 * @param <C> 内容类型
 * @param <R> 返回类型
 * @author kongweiguang
 */
public final class HubImpl<C, R> implements Hub<C, R> {
    private final Map<String, List<MergeWarp<C, R>>> repo = new ConcurrentHashMap<>();

    @Override
    public void push(final Action<C, R> action, final Consumer<R> call) {
        notNull(action, "action must not be null");

        action.callback(call);

        ofNullable(repo.get(action.branch())).ifPresent(ms -> ms.forEach(m -> m.merge(action)));

    }

    @Override
    public void pull(final String branch, final int index, final Merge<Action<C, R>> merge) {
        notNull(branch, "branch must not be null");
        notNull(merge, "merge must not be null");

        final List<MergeWarp<C, R>> merges = repo.computeIfAbsent(branch, k -> new CopyOnWriteArrayList<>());

        merges.add(new MergeWarp<>(index, merge));

        if (merges.size() > 1) {
            merges.sort(Comparator.comparing(MergeWarp::index));
        }
    }

    @Override
    public void pull(final Object obj) {
        notNull(obj, "obj must not be null");

        for (Class<?> c = obj.getClass(); c != null; c = c.getSuperclass()) {
            //跳过object内部的方法，提高性能
            if (c.getName().equals("java.lang.Object")) {
                continue;
            }

            for (Method m : c.getDeclaredMethods()) {
                Pull pull = m.getAnnotation(Pull.class);

                if (pull != null) {
                    Class<?>[] params = m.getParameterTypes();

                    isTure(!(params.length == 0 && pull.value().isEmpty()), "method or branch must have a value ");

                    isTure(params.length <= 1, "method params not > 1");

                    m.setAccessible(true);
                    pull(branch(m, pull, params), pull.index(), mr(obj, m, params, pull.name()));
                }
            }

        }
    }

    private static String branch(final Method m, final Pull pull, final Class<?>[] params) {
        String branch = null;

        if (pull.value().isEmpty()) {

            if (Action.class.isAssignableFrom(params[0])) {

                final List<String> generics = generics(m);

                isTure(!generics.isEmpty(), "action generics must not be null");

                branch = generics.get(0);
            } else {
                branch = params[0].getName();
            }

        } else {
            branch = pull.value();
        }

        return branch;
    }

    private Merge<Action<C, R>> mr(final Object obj, final Method m, final Class<?>[] params, final String name) {
        return new Merge<Action<C, R>>() {
            @Override
            public String name() {
                return defaultIsEmpty(name, Merge.super.name());
            }

            @Override
            public void merge(final Action<C, R> action) throws Exception {
                final Object[] args = new Object[params.length];

                if (params.length == 1) {

                    if (Action.class.isAssignableFrom(params[0])) {
                        args[0] = action;
                    } else {
                        args[0] = action.content();
                    }

                }

                final Object fr = m.invoke(obj, args);

                if (action.hasCallBack()) {
                    action.res((R) fr);
                }
            }
        };
    }

    @Override
    public void remove(final String branch, final String name) {
        notNull(branch, "branch must not be null");
        notNull(name, "name must not be null");

        ofNullable(repo.get(branch)).ifPresent(ms -> ms.removeIf(m -> Objects.equals(m.name(), name)));
    }
}
