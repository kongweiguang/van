package io.github.kongweiguang.van.test;

import io.github.kongweiguang.van.Van;

public class Main {
    public static void main(String[] args) {
        final User user = new User(99, "kpp", new String[]{"1", "2"});

        Van.bus().consumer(User.class, h -> {
            System.out.println(h);
            h.reply("123");
        });

        Van.bus().push(user);

//        Van.<String, String>bus().push(new Msg<>(1, "kong", "ct"), r -> {
//            System.out.println(r);
//        });


    }
}
