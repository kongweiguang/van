package io.github.kongweiguang.van.core;

/**
 * 工具类
 *
 * @author kongweiguang
 */
public final class Util {

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
}
