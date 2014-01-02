package io.snw.entityapi.api.mind.behaviour;

public interface IBehaviourSelector {

    public abstract void addBehaviour(Behaviour behaviour, int priority);

    public abstract void addBehaviour(String key, Behaviour behaviour, int priority);

    public void addAndReplaceBehaviour(String key, Behaviour behaviour, int priority);

    public abstract void removeBehaviour(Behaviour behaviour);

    public abstract void removeBehaviour(String key);

    public abstract void clearBehaviours(String key);

    public abstract Behaviour getBehaviour(String key);

    public abstract void updateBehaviours();
}