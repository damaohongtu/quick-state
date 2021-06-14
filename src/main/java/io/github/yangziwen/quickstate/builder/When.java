package io.github.yangziwen.quickstate.builder;

import io.github.yangziwen.quickstate.Action;

public interface When<S, E, C> {

    void doAction(Action<S, E, C> action);

}
