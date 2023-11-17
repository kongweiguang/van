package io.github.kongweiguang.van.core;

import java.lang.annotation.*;

/**
 * <h1>拉取指定分支的信息</h1>
 * <p>
 * 被标注的方法只能有一个参数，此参数为拉取的操作{@link  Action}
 * <p>
 * 方法如果有返回值则会调用{@link Action} 的res方法，触发推送者的回调方法
 *
 * @author kongweiguang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Pull {
    /**
     * 拉取的分支名称，默认使用方法的第一个参数的全类名
     */
    String value() default "";

    /**
     * 拉取的顺序
     *
     * @return 顺序
     */
    int index() default 0;

    /**
     * 当前拉取者的昵称，可通过昵称取消拉取
     *
     * @return 拉取者的昵称
     */
    String name() default "";
}
