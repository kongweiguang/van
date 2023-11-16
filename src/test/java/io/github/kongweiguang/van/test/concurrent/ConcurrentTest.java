package io.github.kongweiguang.van.test.concurrent;

import io.github.kongweiguang.van.Van;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentTest {
    String topic = "topic.test1";

    @Test
    void test() throws Exception {
        AtomicLong at = new AtomicLong();
        //拉取消息
        Van.bus().pull(topic, h -> at.incrementAndGet());

        final long start = System.currentTimeMillis();
        for (int i = 0; i < 100_000_000; i++) {
            //推送消息
            Van.bus().push(topic, "content");
        }
        final long end = System.currentTimeMillis();
        System.out.println("use time -> " + (end - start) + "ms");
        System.out.println("at = " + at.get());
        //一亿用时，1235ms
    }
}
