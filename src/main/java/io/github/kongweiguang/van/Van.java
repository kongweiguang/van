package io.github.kongweiguang.van;

import io.github.kongweiguang.van.core.Hub;
import io.github.kongweiguang.van.core.HubImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类似git操作的本地eventBus
 *
 * @author kongweiguang
 */
public final class Van {
    private Van() {
        throw new UnsupportedOperationException("van must not be construct");
    }

    private static final Map<String, Hub> hubs = new ConcurrentHashMap<>();
    private static final Hub hub = new HubImpl<>();

    /**
     * 默认的hub
     *
     * @param <C> 操作的内容类型
     * @param <R> 返回的结果类型
     * @return hub
     */
    public static <C, R> Hub<C, R> hub() {
        return (Hub<C, R>) hub;
    }

    /**
     * 自定义hub
     *
     * @param name hub的名称
     * @param <C>  操作的内容类型
     * @param <R>  返回的结果类型
     * @return hub
     */
    public static <C, R> Hub<C, R> hub(final String name) {
        return (Hub<C, R>) hubs.computeIfAbsent(name, k -> new HubImpl<>());
    }
}
