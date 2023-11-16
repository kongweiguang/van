package io.github.kongweiguang.van.test.multi_pull;

import io.github.kongweiguang.van.Van;
import org.junit.jupiter.api.Test;

public class MultiPullTest {
    String topic = "topic.test1";

    @Test
    void test1() throws Exception {
        //拉取消息
        Van.hub().pull(topic, System.out::println);
        Van.hub().pull(topic, System.out::println);

        //推送消息
        Van.hub().push(topic, "content");
    }
}
