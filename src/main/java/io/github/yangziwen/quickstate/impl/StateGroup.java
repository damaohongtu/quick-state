package io.github.yangziwen.quickstate.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.github.yangziwen.quickstate.State;
import io.github.yangziwen.quickstate.Transition;
import lombok.Getter;

public class StateGroup<S> {

    @Getter
    private String name;

    @Getter
    private List<S> stateIds = Collections.emptyList();

    @Getter
    private List<Transition<S, ?, ?>> transitions = new ArrayList<>();

    public StateGroup(String name, S... stateIds) {
        this.stateIds = Arrays.asList(stateIds);
        this.name = name;
    }

    public StateGroup<S> addTransition(Transition<S, ?, ?> transition) {
        this.transitions.add(transition);
        return this;
    }

    public List<Transition<S, ?, ?>> getTransitions(S sourceId, S targetId) {
        return transitions.stream()
                .filter(transition -> transition.getSource().getId() == sourceId)
                .filter(transition -> transition.getTarget().getId() == targetId)
                .collect(Collectors.toList());
    }

    public List<S> getTransitionTargetIds() {
        return transitions.stream()
                .map(Transition::getTarget)
                .map(State::getId)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

}
