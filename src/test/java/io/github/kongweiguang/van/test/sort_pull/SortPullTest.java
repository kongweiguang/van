package io.github.kongweiguang.van.test.sort_pull;

import org.junit.jupiter.api.Test;

import static io.github.kongweiguang.van.Van.hub;

public class SortPullTest {
    String topic = "topic.test1";

    @Test
    void test() throws Exception {
        //拉取消息
        hub().pull(topic, 0, e -> System.out.println("1"));
        hub().pull(topic, -1, e -> System.out.println("2"));


        //推送消息
        hub().push(topic, "content", e -> System.out.println("callback 1 -> " + e));
        //打印结果 2,1
    }
}
