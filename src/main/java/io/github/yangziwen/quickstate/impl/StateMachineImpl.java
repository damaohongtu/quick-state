package io.github.yangziwen.quickstate.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

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
    public void accept(Visitor visitor) {
        visitor.visit(this);
        for (State<S, E, C> state : stateMap.values()) {
            visitor.visit(state);
        }
    }

    @Override
    public S fireEvent(S sourceId, E event, C context) {
        if (!ready) {
            throw new StateMachineException("State machine[" + id + "] is not built yet, can not work");
        }
        State<S, E, C> source = ensureState(sourceId);
        return doTransition(source, event, context).getId();
    }

    private State<S, E, C> doTransition(State<S, E, C> source, E event, C context) {
        List<Transition<S, E, C>> transitions = source.getTransitions(event);
        if (CollectionUtils.isEmpty(transitions)) {
            return source;
        }
        for (Transition<S, E, C> transition : transitions) {
            if (transition.test(context)) {
                return transition.transit(context);
            }
        }
        return source;
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
