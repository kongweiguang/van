package io.github.kongweiguang.van;

import io.github.kongweiguang.van.core.VanBus;
import io.github.kongweiguang.van.core.VanBusImpl;

/**
 * 本地eventBus
 *
 * @author kongweiguang
 */
public final class Van {
    private Van() {
        throw new UnsupportedOperationException("van must not be construct");
    }

    private static final VanBus bus = new VanBusImpl<>();

    public static <C, R> VanBus<C, R> bus() {
        return bus;
    }

}
