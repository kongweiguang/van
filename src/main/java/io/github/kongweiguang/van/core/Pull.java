package io.github.kongweiguang.van.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 将方法注册到消费者组中，<strong>被标注的方法只能有一个参数</strong>
 * <p>
 * 方法如果有返回值则会调用{@link Msg} 的reply方法，触发发布者的回掉方法
 *
 * @author kongweiguang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Pull {
    /**
     * 订阅的topic，默认使用方法的第一个参数的全类名
     */
    String value() default "";
}
