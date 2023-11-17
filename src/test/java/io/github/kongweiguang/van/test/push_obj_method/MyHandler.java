package io.github.kongweiguang.van.test.push_obj_method;

import io.github.kongweiguang.van.core.Pull;
import io.github.kongweiguang.van.test.metedata.User;

import static jdk.nashorn.internal.objects.Global.println;

public class MyHandler {
    @Pull
    public String fn(User user) {
        println(user);
        return "hello";
    }

    @Pull("bala")
    public String fn1() {
        println("fn1");
        return "hello1";
    }

    @Pull("bala")
    public void fn2() {
        println("fn2");
    }
}
