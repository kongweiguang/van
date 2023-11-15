package io.github.kongweiguang.van.test;

import io.github.kongweiguang.van.core.IdGen;

import java.util.IdentityHashMap;

public class Test1 {
    public static void main(String[] args) {
        final long start = System.currentTimeMillis();

        for (int i = 0; i <500_000_000L; i++) {
//            IdGen.of.next();

        System.out.println(IdGen.of.next());
        }
        final long end = System.currentTimeMillis();
        System.out.println("use time -> " + (end - start) + "ms");

    }
}
