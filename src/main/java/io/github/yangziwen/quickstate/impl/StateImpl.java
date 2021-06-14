package io.github.yangziwen.quickstate.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import io.github.yangziwen.quickstate.State;
import io.github.yangziwen.quickstate.Transition;
import io.github.yangziwen.quickstate.TransitionType;
import io.github.yangziwen.quickstate.Visitor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StateImpl<S, E, C> implements State<S, E, C> {

    @EqualsAndHashCode.Include
    private final S id;

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
    public Transition<S, E, C> addTransition(E event, State<S, E, C> target, TransitionType type) {
        TransitionImpl<S, E, C> transition = TransitionImpl.<S, E, C>builder()
                .source(this)
                .target(target)
                .event(event)
                .build();
        verify(event, transition);
        transitions.put(event, transition);
        return transition;
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
