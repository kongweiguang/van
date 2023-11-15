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
    public static final IdGen of = of(1);
    private long base = (System.currentTimeMillis() >> 10) << 30;
    private final AtomicLong add = new AtomicLong();

    private IdGen(final int period) {
        scheduleTask(period);
    }

    public static IdGen of(final int period) {
        return new IdGen(period);
    }

    private void scheduleTask(long period) {
        Executors.newSingleThreadScheduledExecutor(IdGen::newThread)
                .scheduleAtFixedRate(this::updateBaseAndResetAdd, 0, period, TimeUnit.SECONDS);
    }

    private void updateBaseAndResetAdd() {
        this.base = (System.currentTimeMillis() >> 10) << 30;
        this.add.set(0);
    }

    private static Thread newThread(Runnable r) {
        final Thread t = new Thread(r, "idGen");
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