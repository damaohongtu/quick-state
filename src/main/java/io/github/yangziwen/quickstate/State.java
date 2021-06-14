package io.github.yangziwen.quickstate;

import java.util.List;

public interface State<S, E, C> extends Visitable {

    S getId();

    Transition<S, E, C> addTransition(E event, State<S, E, C> target, TransitionType type);

    List<Transition<S, E, C>> getTransitions(E event);

    List<Transition<S, E, C>> getAllTransitions();

}
