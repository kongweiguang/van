package io.github.kongweiguang.van.test.async;

import io.github.kongweiguang.van.Van;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

public class AsyncTest {
    String topic = "topic.test1";

    @Test
    void test() throws Exception {
        //拉取消息
        Van.<String, String>hub().pull(topic, h -> CompletableFuture.runAsync(() -> {
            h.res("123");
            System.out.println(Thread.currentThread().getName());
            System.out.println(h);
        }));

        //推送消息
        Van.<String, String>hub().push(topic, "content", r -> System.out.println(r));
    }
}
