package io.github.kongweiguang.van.test.multi_pull;

import org.junit.jupiter.api.Test;

import static io.github.kongweiguang.van.Van.hub;

public class MultiPullTest {
    String topic = "topic.test1";

    @Test
    void test1() throws Exception {
        //拉取消息
        hub().pull(topic, System.out::println);
        hub().pull(topic, System.out::println);

        //推送消息
        hub().push(topic, "content");
    }
}
