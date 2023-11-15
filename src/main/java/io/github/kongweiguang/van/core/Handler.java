package io.github.kongweiguang.van.core;

@FunctionalInterface
public interface Handler<C, R> {

    void handle(Msg<C, R> msg);
}
