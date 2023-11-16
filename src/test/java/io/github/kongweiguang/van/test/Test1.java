package io.github.kongweiguang.van.test;

import io.github.kongweiguang.van.Van;
import io.github.kongweiguang.van.core.MsgFactory;

import java.util.function.Consumer;

public class Test1 {
    public static void main(String[] args) {
        Van.bus().register(new MyHandler());


        Van.bus().push(MsgFactory.of("bala", new User(1, "k", new String[]{"h"})), new Consumer<Object>() {
            @Override
            public void accept(final Object object) {
                System.out.println("object = " + object);
            }
        });
    }


}
