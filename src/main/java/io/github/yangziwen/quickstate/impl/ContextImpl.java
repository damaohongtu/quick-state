package io.github.yangziwen.quickstate.impl;

import io.github.yangziwen.quickstate.Context;
import io.github.yangziwen.quickstate.StateMachine;
import io.github.yangziwen.quickstate.Transition;
import lombok.Getter;
import lombok.Setter;

public class ContextImpl<S, E, C extends ContextImpl<S, E, C>> implements Context<S, E, C> {

    @Getter
    @Setter(lombok.AccessLevel.PACKAGE)
    private Transition<S, E, C> transition;

    @Getter
    @Setter(lombok.AccessLevel.PACKAGE)
    private StateMachine<S, E, C> stateMachine;


}
