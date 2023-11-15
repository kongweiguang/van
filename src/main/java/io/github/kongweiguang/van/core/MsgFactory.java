package io.github.kongweiguang.van.core;

public class MsgFactory {

    public static <C, R> Msg<C, R> of(final String topic, final C c) {
        return new Msg<>(IdGen.of.next(), topic, c);
    }

    public static <C, R> Msg<C, R> of(final long id, final String topic, final C c) {
        return new Msg<>(id, topic, c);
    }

}
