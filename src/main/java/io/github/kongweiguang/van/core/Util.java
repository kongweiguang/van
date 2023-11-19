package io.github.kongweiguang.van.core;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 工具类
 *
 * @author kongweiguang
 */
final class Util {

    public static boolean isEmpty(final String str) {
        if (str == null) {
            return true;
        }

        return str.isEmpty();
    }

    public static String defaultIsEmpty(final String str, final String d) {
        if (isEmpty(str)) {
            return d;
        }

        return str;
    }

    public static void notNull(final Object obj, final String msg) {
        if (obj == null) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void isTure(final boolean bool, final String msg) {
        if (!bool) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static List<String> generics(Method m) {
        List<String> fr = new ArrayList<>(2);
        Type[] genericParameterTypes = m.getGenericParameterTypes();
        for (Type type : genericParameterTypes) {
            if (type instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                for (Type actualType : actualTypeArguments) {
                    fr.add(actualType.getTypeName());
                }
            }
        }
        return fr;
    }
}
