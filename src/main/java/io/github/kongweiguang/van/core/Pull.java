package io.github.kongweiguang.van.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h1>拉取指定分支的信息</h1>
 * <p>
 * <b>被标注的方法只能有一个参数</b>
 * <p>
 * 方法如果有返回值则会调用{@link Action} 的res方法，触发推送者的回调方法
 *
 * @author kongweiguang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Pull {
    /**
     * 拉取的分支名称，默认使用方法的第一个参数的全类名
     */
    String value() default "";
}
