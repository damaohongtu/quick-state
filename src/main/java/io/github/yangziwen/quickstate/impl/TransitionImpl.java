package io.github.yangziwen.quickstate.impl;

import io.github.yangziwen.quickstate.Action;
import io.github.yangziwen.quickstate.Condition;
import io.github.yangziwen.quickstate.State;
import io.github.yangziwen.quickstate.Transition;
import io.github.yangziwen.quickstate.TransitionType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TransitionImpl<S, E, C> implements Transition<S, E, C> {

    @EqualsAndHashCode.Include
    private State<S, E, C> source;

    @EqualsAndHashCode.Include
    private State<S, E, C> target;

    @EqualsAndHashCode.Include
    private E event;

    @Builder.Default
    private Condition<C> condition = null;

    @Builder.Default
    private Action<S, E, C> action = null;

    @Builder.Default
    private TransitionType type = TransitionType.EXTERNAL;

    @Override
    public boolean test(C context) {
        return condition == null || condition.test(context);
    }

    @Override
    @SuppressWarnings("unchecked")
    public State<S, E, C> transit(C context) {
        this.verify();
        if (!test(context)) {
            return null;
        }
        if (context instanceof ContextImpl) {
            ContextImpl.class.cast(context).setTransition(this);
        }
        if (action != null) {
            action.execute(source.getId(), target.getId(), event, context);
        }
        return target;
    }

    @Override
    public void verify() {
        if (type == TransitionType.INTERNAL && source != target) {
            throw new StateMachineException(String.format("Internal transition source state '%s' " +
                    "and target state '%s' must be same.", source, target));
        }
    }

    @Override
    public String toString() {
        return source + "-[" + event.toString() + ", " + type + "]->" + target;
    }

}
