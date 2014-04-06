package org.entityapi.api.mind;

public class BehaviourItem {

    private Behaviour behaviour;
    private int priority;

    public BehaviourItem(Behaviour behaviour, int priority) {
        this.behaviour = behaviour;
        this.priority = priority;
    }

    public Behaviour getBehaviour() {
        return behaviour;
    }

    public int getPriority() {
        return priority;
    }
}