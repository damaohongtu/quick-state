package io.github.yangziwen.quickstate;

public interface Transition<S, E, C> {

    State<S, E, C> getSource();

    State<S, E, C> getTarget();

    E getEvent();

    Condition<C> getCondition();

    Action<S, E, C> getAction();

    <V> V getConfigValue(String key);

    boolean test(C context);

    State<S, E, C> transit(C context);

    void verify();

}
