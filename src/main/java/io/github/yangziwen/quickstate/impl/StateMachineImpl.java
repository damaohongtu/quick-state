package io.github.yangziwen.quickstate.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import io.github.yangziwen.quickstate.State;
import io.github.yangziwen.quickstate.StateMachine;
import io.github.yangziwen.quickstate.Transition;
import io.github.yangziwen.quickstate.Visitor;
import lombok.Getter;
import lombok.Setter;

public class StateMachineImpl<S, E, C> implements StateMachine<S, E, C> {

    @Getter
    private final String id;

    private final Map<S, State<S, E, C>> stateMap;

    @Getter
    @Setter
    private boolean ready;

    public StateMachineImpl(String id, Map<S, State<S, E, C>> stateMap) {
        this.id = id;
        this.stateMap = stateMap;
    }

    @Override
    public List<State<S, E, C>> getAllStates() {
        return new ArrayList<>(stateMap.values());
    }

    @Override
    public List<Transition<S, E, C>> getTransitions(S sourceId) {
        State<S, E, C> source = ensureState(sourceId);
        return source.getAllTransitions();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public S fire(S sourceId, E event, C context) {
        if (!ready) {
            throw new StateMachineException("State machine[" + id + "] is not built yet, can not work");
        }
        if (context instanceof ContextImpl) {
            ContextImpl.class.cast(context).setStateMachine(this);
        }
        State<S, E, C> source = ensureState(sourceId);
        State<S, E, C> target = doTransition(source, event, context);
        return target != null ? target.getId() : null;
    }

    @Override
    public boolean canApply(S sourceId, E event) {
        State<S, E, C> source = ensureState(sourceId);
        List<Transition<S, E, C>> transitions = source.getTransitions(event);
        return CollectionUtils.isNotEmpty(transitions);
    }

    private State<S, E, C> doTransition(State<S, E, C> source, E event, C context) {
        List<Transition<S, E, C>> transitions = source.getTransitions(event);
        if (CollectionUtils.isEmpty(transitions)) {
            return null;
        }
        for (Transition<S, E, C> transition : transitions) {
            if (transition.test(context)) {
                return transition.transit(context);
            }
        }
        return null;
    }

    private State<S, E, C> ensureState(S stateId) {
        State<S, E, C> state = stateMap.get(stateId);
        if(state == null) {
            state = new StateImpl<>(stateId);
            stateMap.put(stateId, state);
        }
        return state;
    }

    @Override
    public String toString() {
        return "state machine[" + getId() + "] with states [" + StringUtils.join(stateMap.keySet().toArray(), ", ") + "]";
    }

}
