package io.github.kongweiguang.van.test.push_topic;

import io.github.kongweiguang.van.Van;
import io.github.kongweiguang.van.core.Action;
import org.junit.jupiter.api.Test;

public class PushTopicTest {
    String topic = "topic.test1";

    @Test
    void test() throws Exception {
        //拉取消息
        Van.hub().pull(topic, System.out::println);


        //推送消息
        Van.hub().push(topic, "content");
        Van.hub().push(topic, "content", e -> System.out.println("callback 1 -> " + e));
        Van.hub().push(Action.of(topic, "content"));
        Van.hub().push(Action.of(topic, "content"), e -> System.out.println("callback 2 -> " + e));
    }
}
