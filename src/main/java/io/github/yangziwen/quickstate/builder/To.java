package io.github.yangziwen.quickstate.builder;

import java.util.Map;

public interface To<S, E, C> {

    On<S, E, C> on(E event);

    On<S, E, C> on(E event, Map<String, Object> config);

}
