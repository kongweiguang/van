package io.github.kongweiguang.van;

import io.github.kongweiguang.van.core.VanEventBus;
import io.github.kongweiguang.van.core.VanEventBusImpl;

/**
 * 本地eventBus
 *
 * @author kongweiguang
 */
public class Van {
    private static final VanEventBus bus = new VanEventBusImpl<>();

    public static <C, R> VanEventBus<C, R> bus() {
        return bus;
    }

}
