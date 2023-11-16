package io.github.kongweiguang.van.test;

import io.github.kongweiguang.van.Van;

import java.util.concurrent.atomic.AtomicLong;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final User user = new User(99, "kpp", new String[]{"1", "2"});
        AtomicLong add = new AtomicLong();
        Van.bus().consumer(User.class, h -> {
            add.incrementAndGet();
            h.reply("123");
        });

        Van.bus().consumer(User.class, h -> {
            add.incrementAndGet();
            h.reply("123");
        });

        final long start = System.currentTimeMillis();
        for (int i = 0; i < 100_000_000; i++) {
            Van.bus().push(user);
        }

        System.out.println(add.get());
        final long end = System.currentTimeMillis();
        System.out.println("use time -> " + (end - start) + "ms");


    }
}
