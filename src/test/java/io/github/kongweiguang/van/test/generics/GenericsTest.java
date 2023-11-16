package io.github.kongweiguang.van.test.generics;

import io.github.kongweiguang.van.Van;
import io.github.kongweiguang.van.test.metedata.User;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class GenericsTest {
    String topic = "topic.test1";

    @Test
    void test() throws Exception {
        //拉取消息
        Van.<User, List<String>>bus().pull(topic, h -> {
            final User user = h.content();
            System.out.println("user = " + user);
            h.reply(Collections.singletonList(user.hobby()[0]));
        });


        //推送消息
        Van.<User, List<String>>bus().push(topic, new User(1, "kpp", new String[]{"code"}), c -> {
            System.out.println(c.size());
            System.out.println(c.get(0));
        });
    }
}
