package io.github.yangziwen.quickstate.visitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import io.github.yangziwen.quickstate.Condition;
import io.github.yangziwen.quickstate.Renderer;
import io.github.yangziwen.quickstate.State;
import io.github.yangziwen.quickstate.StateMachine;
import io.github.yangziwen.quickstate.Transition;
import io.github.yangziwen.quickstate.Visitable;
import io.github.yangziwen.quickstate.Visitor;

public class GraphvizVisitor implements Visitor, Renderer {

    private List<String> transitionList = new ArrayList<>();

    private Set<State<?, ?, ?>> states = new HashSet<>();

    private StateMachine<?, ?, ?> stateMachine;

    @Override
    public void visit(Visitable visitable) {
        if (visitable instanceof StateMachine) {
            visitStateMachine(StateMachine.class.cast(visitable));
        } else if (visitable instanceof State) {
            visitState(State.class.cast(visitable));
        }
    }

    private void visitStateMachine(StateMachine<?, ?, ?> stateMachine) {
        this.stateMachine = stateMachine;
        stateMachine.getAllStates().forEach(this::visit);
    }

    private void visitState(State<?, ?, ?> state) {
        StringBuilder builder = new StringBuilder();
        for (Transition<?, ?, ?> transition : state.getAllTransitions()) {
            Object event = transition.getEvent();
            Condition<?> condition = transition.getCondition();
            builder.append("    ")
                    .append(transition.getSource().getId()).append(" -> ").append(transition.getTarget().getId())
                    .append(" [ label = \"").append(event);
            if (condition != null && StringUtils.isNotBlank(condition.getDescription())) {
                builder.append("\\n")
                    .append("condition: ").append(condition.getDescription());
            }
            builder.append("\" ]; \n");
            states.add(transition.getSource());
            states.add(transition.getTarget());
        }
        transitionList.add(builder.toString());
    }

    @Override
    public String render() {
        StringBuilder builder = new StringBuilder();
        builder.append("digraph ").append(stateMachine.getId()).append(" {\n")
                .append("    ").append("size = \"8,5\"\n");
        for (State<?, ?, ?> state : states) {
            builder.append("    ")
                    .append(state.getId()).append("[ shape = box ];\n");
        }
        for (String transition : transitionList) {
            builder.append(transition);
        }
        builder.append("}");
        return builder.toString();
    }

}
