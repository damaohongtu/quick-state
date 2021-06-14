package io.github.yangziwen.quickstate;

public interface Condition<C> {

    boolean test(C context);

    default String getName() {
        return this.getClass().getSimpleName();
    }

    String getDescription();

}
