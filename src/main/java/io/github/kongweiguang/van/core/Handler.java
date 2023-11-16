package io.github.kongweiguang.van.core;

/**
 * 处理器
 *
 * @param <C>
 * @param <R>
 * @author kongweiguang
 */
@FunctionalInterface
public interface Handler<C, R> {
    default String name() {
        return null;
    }

    void handle(Msg<C, R> msg) throws Exception;
}
