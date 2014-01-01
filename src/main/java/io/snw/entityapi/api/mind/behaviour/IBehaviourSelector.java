package io.snw.entityapi.api.mind.behaviour;

public interface IBehaviourSelector {

    public abstract void addBehaviour(Behaviour behaviour, int priority);

    public abstract void removeBehaviour(Behaviour behaviour);

    public abstract Behaviour removeBehaviour(String key);

    public abstract Behaviour clearBehaviours(String key);

    public abstract Behaviour getBehaviour(String key);

    public abstract void updateBehaviours();
}