package io.github.yangziwen.quickstate;

public interface Context<S, E, C> {

    Transition<S, E, C> getTransition();

    StateMachine<S, E, C> getStateMachine();

}
