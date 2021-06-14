package io.github.yangziwen.quickstate.impl;

import java.util.Map;

import io.github.yangziwen.quickstate.Action;
import io.github.yangziwen.quickstate.Condition;
import io.github.yangziwen.quickstate.State;
import io.github.yangziwen.quickstate.Transition;
import io.github.yangziwen.quickstate.TransitionType;
import io.github.yangziwen.quickstate.builder.ExternalTransitionBuilder;
import io.github.yangziwen.quickstate.builder.From;
import io.github.yangziwen.quickstate.builder.InternalTransitionBuilder;
import io.github.yangziwen.quickstate.builder.On;
import io.github.yangziwen.quickstate.builder.To;
import io.github.yangziwen.quickstate.builder.When;

public class TransitionBuilderImpl<S, E, C> implements
        ExternalTransitionBuilder<S, E, C>,
        InternalTransitionBuilder<S, E, C>,
        From<S, E, C>, To<S, E, C>, On<S, E, C>, When<S, E, C> {

    private final Map<S, State<S, E, C>> stateMap;

    private final TransitionType transitionType;

    private State<S, E, C> source;

    private State<S, E, C> target;

    private Transition<S, E, C> transition;

    public TransitionBuilderImpl(Map<S, State<S, E, C>> stateMap, TransitionType transitionType) {
        this.stateMap = stateMap;
        this.transitionType = transitionType;
    }

    @Override
    public From<S, E, C> from(S stateId) {
        this.source = ensureState(stateId);
        return this;
    }

    @Override
    public To<S, E, C> to(S stateId) {
        this.target = ensureState(stateId);
        return this;
    }

    @Override
    public To<S, E, C> within(S stateId) {
        this.source = this.target = ensureState(stateId);
        return this;
    }

    @Override
    public On<S, E, C> on(E event) {
        this.transition = source.addTransition(event, target, transitionType);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public When<S, E, C> when(Condition<C> condition) {
        TransitionImpl.class.cast(transition).setCondition(condition);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public When<S, E, C> when(Condition<C> condition, String description) {
        ConditionProxy<C> proxy = new ConditionProxy<>(condition, description);
        TransitionImpl.class.cast(transition).setCondition(proxy);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doAction(Action<S, E, C> action) {
        TransitionImpl.class.cast(transition).setAction(action);
    }

    private State<S, E, C> ensureState(S stateId) {
        State<S, E, C> state = stateMap.get(stateId);
        if(state == null) {
            state = new StateImpl<>(stateId);
            stateMap.put(stateId, state);
        }
        return state;
    }

}
