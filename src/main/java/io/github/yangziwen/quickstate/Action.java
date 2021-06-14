package io.github.yangziwen.quickstate;

public interface Action<S, E, C> {

    void execute(S source, S target, E event, C context);

}
