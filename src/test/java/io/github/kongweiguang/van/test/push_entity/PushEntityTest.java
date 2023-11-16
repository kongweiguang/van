package io.github.kongweiguang.van.test.push_entity;

import io.github.kongweiguang.van.Van;
import io.github.kongweiguang.van.test.metedata.User;
import org.junit.jupiter.api.Test;

public class PushEntityTest {

    @Test
    void test1() throws Exception {
        final User user = new User(99, "kpp", new String[]{"1", "2"});

        Van.bus().pull(User.class, h -> {
            System.out.println(h);
            h.reply("123");
        });

        Van.bus().push(user);

    }
}
