package io.github.yangziwen.quickstate.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import io.github.yangziwen.quickstate.State;
import io.github.yangziwen.quickstate.Transition;
import io.github.yangziwen.quickstate.TransitionType;
import io.github.yangziwen.quickstate.Visitor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StateImpl<S, E, C> implements State<S, E, C> {

    @EqualsAndHashCode.Include
    private final S id;

    private List<StateGroup<S>> groups = new ArrayList<>();

    private ListMultimap<E, Transition<S, E, C>> transitions = ArrayListMultimap.create();

    StateImpl(S id) {
        this.id = id;
    }

    @Override
    public S getId() {
        return id;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Transition<S, E, C> addTransition(
            E event, State<S, E, C> target, TransitionType type, Map<String, Object> config) {
        TransitionImpl<S, E, C> transition = TransitionImpl.<S, E, C>builder()
                .source(this)
                .target(target)
                .event(event)
                .build();
        verify(event, transition);
        if (config != null) {
            transition.getConfig().putAll(config);
        }
        transitions.put(event, transition);
        return transition;
    }

    @Override
    public StateImpl<S, E, C> addStateGroup(StateGroup<S> group) {
        if (group != null) {
            this.groups.add(group);
        }
        return this;
    }

    @Override
    public List<StateGroup<S>> getStateGroups() {
        return this.groups;
    }

    @Override
    public List<Transition<S, E, C>> getTransitions(E event) {
        return transitions.get(event);
    }

    @Override
    public List<Transition<S, E, C>> getAllTransitions() {
        return new ArrayList<>(transitions.values());
    }

    private void verify(E event, Transition<S, E, C> transition) {
        if(transitions.values().contains(transition)){
            throw new StateMachineException(transition + " already Exist, you can not add another one");
        }
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
