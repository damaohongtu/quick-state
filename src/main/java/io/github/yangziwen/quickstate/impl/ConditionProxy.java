package io.github.yangziwen.quickstate.impl;

import io.github.yangziwen.quickstate.Condition;

public class ConditionProxy<C> implements Condition<C> {

    private Condition<C> condition;

    private String description;

    public ConditionProxy(Condition<C> condition, String description) {
        this.condition = condition;
        this.description = description;
    }

    @Override
    public boolean test(C context) {
        return condition.test(context);
    }

    @Override
    public String getDescription() {
        return description;
    }

}
