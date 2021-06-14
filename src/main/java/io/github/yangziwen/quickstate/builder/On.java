package io.github.yangziwen.quickstate.builder;

import io.github.yangziwen.quickstate.Condition;

public interface On<S, E, C> {

    When<S, E, C> when(Condition<C> condition);

    When<S, E, C> when(Condition<C> condition, String description);

}
