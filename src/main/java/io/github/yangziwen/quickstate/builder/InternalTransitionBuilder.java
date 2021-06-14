package io.github.yangziwen.quickstate.builder;

public interface InternalTransitionBuilder<S, E, C> {

    To<S, E, C> within(S stateId);

}
