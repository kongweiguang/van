package io.github.kongweiguang.van.test;

import io.github.kongweiguang.van.Van;
import io.github.kongweiguang.van.core.MsgFactory;

public class Test1 {
    public static void main(String[] args) {
        Van.bus().register(new MyHandler());

        Van.bus().push(MsgFactory.of("bala", new User(1, "k", new String[]{"h"})), object -> System.out.println("object = " + object));
        Van.bus().push("bala1", new User(1, "k", new String[]{"h"}), object -> System.out.println("object = " + object));
        Van.bus().push(new User(1, "k", new String[]{"h"}), object -> System.out.println("object = " + object));

    }


}
