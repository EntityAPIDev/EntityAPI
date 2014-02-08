package io.snw.entityapi.api;

public enum DespawnReason {

    DEATH(0),
    PLUGIN_DISABLE(1),
    UNKNOWN(2);

    private final int priority;

    DespawnReason(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }
}
