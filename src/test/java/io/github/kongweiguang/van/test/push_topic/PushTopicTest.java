package io.github.kongweiguang.van.test.push_topic;

import io.github.kongweiguang.van.core.Action;
import org.junit.jupiter.api.Test;

import static io.github.kongweiguang.van.Van.hub;

public class PushTopicTest {
    String topic = "topic.test1";

    @Test
    void test() throws Exception {
        //拉取消息
        hub().pull(topic, System.out::println);


        //推送消息
        hub().push(topic, "content");
        hub().push(topic, "content", e -> System.out.println("callback 1 -> " + e));
        hub().push(Action.of(topic, "content"));
        hub().push(Action.of(topic, "content"), e -> System.out.println("callback 2 -> " + e));
    }
}
