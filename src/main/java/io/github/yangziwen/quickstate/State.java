package io.github.yangziwen.quickstate;

import java.util.List;

import io.github.yangziwen.quickstate.impl.StateGroup;

public interface State<S, E, C> extends Visitable {

    S getId();

    Transition<S, E, C> addTransition(E event, State<S, E, C> target, TransitionType type);

    List<Transition<S, E, C>> getTransitions(E event);

    List<Transition<S, E, C>> getAllTransitions();

    State<S, E, C> addStateGroup(StateGroup<S> group);

    List<StateGroup<S>> getStateGroups();

}
