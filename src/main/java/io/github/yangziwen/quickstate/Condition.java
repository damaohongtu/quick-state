package io.github.yangziwen.quickstate;

public interface Condition<C> {

    boolean test(C context);

    default String getName() {
        return this.getClass().getSimpleName();
    }

    default String getDescription() {
        return "";
    }

}
