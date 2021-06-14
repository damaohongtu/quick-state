package io.github.yangziwen.quickstate.builder;

import java.util.Map;

import io.github.yangziwen.quickstate.State;
import io.github.yangziwen.quickstate.StateMachine;
import io.github.yangziwen.quickstate.StateMachineFactory;
import io.github.yangziwen.quickstate.TransitionType;
import io.github.yangziwen.quickstate.impl.TransitionBuilderImpl;

public interface StateMachineBuilder<S, E, C, M extends StateMachine<S, E, C>> {

    default ExternalTransitionBuilder<S, E, C> externalTransition() {
        return new TransitionBuilderImpl<>(getStateMap(), TransitionType.EXTERNAL);
    }

    default InternalTransitionBuilder<S, E, C> internalTransition() {
        return new TransitionBuilderImpl<>(getStateMap(), TransitionType.INTERNAL);
    }

    default M build(String machineId) {
        M stateMachine = newStateMachine(machineId, getStateMap());
        StateMachineFactory.register(stateMachine);
        return stateMachine;
    }

    M newStateMachine(String machineId, Map<S, State<S, E, C>> stateMap);

    Map<S, State<S, E,C>> getStateMap();

}
