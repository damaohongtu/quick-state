package io.github.yangziwen.quickstate.builder;

public interface ExternalTransitionBuilder<S, E, C> {

    From<S, E, C> from(S... stateId);

}
