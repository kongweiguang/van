package io.github.kongweiguang.van.test.del_pull;

import io.github.kongweiguang.van.core.Action;
import io.github.kongweiguang.van.core.Merge;
import org.junit.jupiter.api.Test;

import static io.github.kongweiguang.van.Van.hub;

public class DelPullTest {
    String topic = "topic.test1";

    @Test
    void test() throws Exception {
        //拉取消息
        hub().pull(topic, new Merge<Action<Object, Object>>() {
            @Override
            public String name() {
                return "k_pull";
            }

            @Override
            public void merge(final Action<Object, Object> a) throws Exception {
                System.out.println(a);
            }
        });


        //推送消息
        hub().push(topic, "content");

        //删除拉取
        hub().remove(topic, "k_pull");

        //推送消息
        hub().push(topic, "content");

    }
}
