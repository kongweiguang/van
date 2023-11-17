package io.github.kongweiguang.van.test.custom;

import org.junit.jupiter.api.Test;

import static io.github.kongweiguang.van.Van.hub;

public class CustomTest {
    String topic = "topic.test1";

    @Test
    void test() throws Exception {
        //拉取消息
        hub("hub1").pull(topic, System.out::println);
        //拉取默认的消息
        hub().pull(topic, System.out::println);


        //推送消息
        hub("hub1").push(topic, "content", e -> System.out.println("callback 1 -> " + e));
    }
}
