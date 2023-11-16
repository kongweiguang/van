package io.github.kongweiguang.van.core;

/**
 * 处理器
 *
 * @author kongweiguang
 */
@FunctionalInterface
public interface Merge<repo> {
    /**
     * 处理器昵称
     *
     * @return 昵称
     */
    default String name() {
        return null;
    }

    /**
     * 处理消息
     *
     * @param msg 消息
     * @throws Exception 异常
     */
    void merge(repo msg) throws Exception;
}
