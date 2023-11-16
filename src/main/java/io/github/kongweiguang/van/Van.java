package io.github.kongweiguang.van;

import io.github.kongweiguang.van.core.Hub;
import io.github.kongweiguang.van.core.HubImpl;

/**
 * 本地eventBus
 *
 * @author kongweiguang
 */
public final class Van {
    private Van() {
        throw new UnsupportedOperationException("van must not be construct");
    }

    private static final Hub hub = new HubImpl<>();

    public static <C, R> Hub<C, R> hub() {
        return hub;
    }

}
