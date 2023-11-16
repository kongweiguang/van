package io.github.kongweiguang.van.test.attr;

import io.github.kongweiguang.van.Van;
import io.github.kongweiguang.van.core.MsgFactory;
import org.junit.jupiter.api.Test;

public class AttrTest {
    String topic = "topic.test1";

    @Test
    void test() throws Exception {
        //拉取消息
        Van.<String, String>bus().pull(topic, System.out::println);

        //推送消息
        Van.<String, Void>bus().push(MsgFactory.<String, Void>of(topic, "content").attr("k", "v"));
    }
}
