package io.github.kongweiguang.van.core;

/**
 * 合并器包装，可以给合并器排序，拉取时可以指定那些pull先执行
 *
 * @param <C> 操作内容类型
 * @param <R> 返回结果类型
 */
final class MergeWarp<C, R> {
    private final int index;
    private final Merge<Action<C, R>> merge;

    public MergeWarp(final int index, final Merge<Action<C, R>> merge) {
        this.index = index;
        this.merge = merge;
    }

    /**
     * 执行的顺序
     *
     * @return 位置
     */
    public int index() {
        return index;
    }

    /**
     * 合并器名称
     *
     * @return 名称
     */
    public String name() {
        return merge.name();
    }

    /**
     * 合并操作
     *
     * @param action 操作
     */
    void merge(Action<C, R> action) {
        try {
            merge.merge(action);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
