package io.github.kongweiguang.van.core;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class IdGen {
    public static final IdGen of = new IdGen(1);

    private long base;
    private final AtomicLong add = new AtomicLong();

    private IdGen(int period) {
        this.base = System.currentTimeMillis() / 1000;

        Executors.newSingleThreadScheduledExecutor(IdGen::newThread)
                .scheduleAtFixedRate(() -> {
                            base = (System.currentTimeMillis() / 1000) * 1_000_000_000;
                            add.set(0);
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

    public long next() {
        return base + add.incrementAndGet();
    }
}