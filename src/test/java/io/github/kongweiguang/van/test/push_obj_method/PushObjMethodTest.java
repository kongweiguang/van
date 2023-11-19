package io.github.kongweiguang.van.test.push_obj_method;

import io.github.kongweiguang.van.Van;
import io.github.kongweiguang.van.core.Action;
import io.github.kongweiguang.van.test.metedata.User;
import org.junit.jupiter.api.Test;

import static io.github.kongweiguang.van.Van.hub;

public class PushObjMethodTest {
    @Test
    void test1() throws Exception {
        //设置拉取消息的处理
        hub().pull(new MyHandler());

        //推送tipic为bala的消息
        hub().push(Action.of("bala", new User(1, "k", new String[]{"h"})), object -> System.out.println("object = " + object));

        //推送topic为bala1的消息
        hub().push("bala1", new User(1, "k", new String[]{"h"}), object -> System.out.println("object = " + object));

        //推送user类的topic
        hub().push(new User(1, "k", new String[]{"h"}), object -> System.out.println("object = " + object));

        Van.<User, String>hub().push(new User(1, "k", new String[]{"h"}), object -> System.out.println("object = " + object));

    }
}
