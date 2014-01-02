package io.snw.entityapi.api.mind.behaviour;

public abstract class Behaviour {

    public abstract BehaviourType getType();

    public abstract boolean shouldStart();

    public boolean shouldContinue() {
        return !shouldStart();
    }

    public void start() {
    }

    public void finish() {
    }

    public boolean isContinuous() {
        return true;
    }

    public abstract String getDefaultKey();

    public abstract void tick();
}
