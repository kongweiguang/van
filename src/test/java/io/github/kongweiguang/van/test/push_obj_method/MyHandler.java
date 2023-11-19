package io.github.kongweiguang.van.test.push_obj_method;

import io.github.kongweiguang.van.core.Action;
import io.github.kongweiguang.van.core.Pull;
import io.github.kongweiguang.van.test.metedata.User;

public class MyHandler {
    @Pull
    public String fn(User user) {
        System.out.println(user);
        return "hello";
    }


    @Pull("bala")
    public String fn1() {
        System.out.println("fn1");
        return "hello1";
    }

    @Pull("bala")
    public void fn2() {
        System.out.println("fn2");
    }

    @Pull
    public String fn3(Action<User, String> action) {
        System.out.println(action);
        return "hello2";
    }

}
