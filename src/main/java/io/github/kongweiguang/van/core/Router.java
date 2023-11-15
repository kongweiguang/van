package io.github.kongweiguang.van.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Router<C,R> {
    private final Map<Object, List<Handler<C,R>>> router = new HashMap<>();

    public void add(Object topic, Handler<C,R> handler) {
        router.computeIfAbsent(topic, k -> new ArrayList<>()).add(handler);
    }

    public List<Handler<C,R>> get(Object topic) {
        return router.computeIfAbsent(topic, k -> new ArrayList<>());
    }

}
