package io.github.kongweiguang.van.core;

public class MsgFactory {

    public static <C, R> Msg<C, R> of(final String topic, final C c) {
        return new Msg<>(IdGen.of.next(), topic, c);
    }

}
