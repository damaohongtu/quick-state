package io.github.yangziwen.quickstate;

import java.util.List;

public interface StateMachine<S, E, C> extends Visitable {

    String getId();

    List<State<S, E, C>> getAllStates();

    S fireEvent(S source, E event, C context);

}
