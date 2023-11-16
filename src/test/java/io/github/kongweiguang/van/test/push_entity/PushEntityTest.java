package io.github.kongweiguang.van.test.push_entity;

import io.github.kongweiguang.van.test.metedata.User;
import org.junit.jupiter.api.Test;

import static io.github.kongweiguang.van.Van.hub;

public class PushEntityTest {

    @Test
    void test1() throws Exception {
        final User user = new User(99, "kpp", new String[]{"1", "2"});

        hub().pull(User.class, h -> {
            System.out.println(h);
            h.res("123");
        });

        hub().push(user);

    }
}
