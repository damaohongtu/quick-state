package io.github.yangziwen.quickstate;

public interface StateMachine<S, E, C> extends Visitable {

    String getId();

    S fireEvent(S source, E event, C context);

}
