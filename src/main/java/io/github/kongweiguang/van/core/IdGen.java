package io.github.kongweiguang.van.core;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * id生成器
 *
 * @author kongweiguang
 */
public final class IdGen {
    public static final IdGen of = new IdGen(1);
    private long base;
    private final AtomicLong add = new AtomicLong();

    private IdGen(int period) {
        this.base = System.currentTimeMillis() / 1000;

        Executors.newSingleThreadScheduledExecutor(IdGen::newThread)
                .scheduleAtFixedRate(() -> {
                            this.base = (System.currentTimeMillis() >> 10) << 30;
                            this.add.set(0);
                        },
                        period,
                        period,
                        TimeUnit.MILLISECONDS
                );
    }

    private static Thread newThread(Runnable f) {
        final Thread t = new Thread();
        t.setName("IdGen");
        t.setDaemon(true);
        return t;
    }

    /**
     * 生成id
     *
     * @return id
     */
    public long next() {
        return base + add.incrementAndGet();
    }
}