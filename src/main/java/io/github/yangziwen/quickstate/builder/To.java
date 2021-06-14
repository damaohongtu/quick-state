package io.github.yangziwen.quickstate.builder;

public interface To<S, E, C> {

    On<S, E, C> on(E event);

}
