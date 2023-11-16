package io.github.kongweiguang.van.test;

import io.github.kongweiguang.van.core.Consumer;

public class MyHandler {

    @Consumer
    public String fn(User user) {
        System.out.println(user);
        return "hello";
    }

    @Consumer("bala")
    public String fn1() {
        System.out.println("fn1");
        return "hello1";
    }

    @Consumer("bala")
    public void fn2() {
        System.out.println("fn2");
    }
}
