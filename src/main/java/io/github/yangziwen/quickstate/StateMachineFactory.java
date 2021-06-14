package io.github.yangziwen.quickstate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.yangziwen.quickstate.impl.StateMachineException;

public class StateMachineFactory {

    @SuppressWarnings("rawtypes")
    private static Map<String /* machineId */, StateMachine> stateMachineMap = new ConcurrentHashMap<>();

    public static <S, E, C> void register(StateMachine<S, E, C> stateMachine) {
        String machineId = stateMachine.getId();
        if (stateMachineMap.containsKey(machineId)) {
            throw new StateMachineException("The state machine with id [" + machineId + "] is already built, no need to build again");
        }
    }

    @SuppressWarnings("unchecked")
    public static <S, E, C> StateMachine<S, E, C> get(String machineId) {
        StateMachine<S, E, C> stateMachine = stateMachineMap.get(machineId);
        if(stateMachine == null){
            throw new StateMachineException("There is no stateMachine instance for " + machineId + ", please build it first");
        }
        return stateMachine;
    }

}
