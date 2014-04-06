package org.entityapi.api.mind;

public interface Behaviour {

    public abstract BehaviourType getType();

    public abstract String getDefaultKey();

    public abstract boolean shouldStart();

    public boolean shouldContinue();

    public void start();

    public void finish();

    public boolean isContinuous();

    public void tick();
}
