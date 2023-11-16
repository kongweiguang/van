package io.github.kongweiguang.van.test.push_topic;

import io.github.kongweiguang.van.Van;
import io.github.kongweiguang.van.core.Msg;
import org.junit.jupiter.api.Test;

public class PushTopicTest {
    String topic = "topic.test1";

    @Test
    void test1() throws Exception {
        //拉取消息
        Van.bus().pull(topic, System.out::println);


        //推送消息
        Van.bus().push(topic, "content");
        Van.bus().push(topic, "content", e -> System.out.println("callback 1 -> " + e));
        Van.bus().push(Msg.of(topic, "content"));
        Van.bus().push(Msg.of(topic, "content"), e -> System.out.println("callback 2 -> " + e));
    }
}
