package io.github.yangziwen.quickstate.builder;

public interface From<S, E, C> {

    To<S, E, C> to(S stateId);

}
