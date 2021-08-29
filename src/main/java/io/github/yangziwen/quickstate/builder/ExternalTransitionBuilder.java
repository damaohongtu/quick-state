package io.github.yangziwen.quickstate.builder;

import io.github.yangziwen.quickstate.impl.StateGroup;

public interface ExternalTransitionBuilder<S, E, C> {

    From<S, E, C> from(S... stateId);

    From<S, E, C> from(StateGroup<S> group);

}
