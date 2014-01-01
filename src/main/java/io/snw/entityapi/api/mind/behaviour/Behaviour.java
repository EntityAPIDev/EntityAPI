package io.snw.entityapi.api.mind.behaviour;

public abstract class Behaviour {

    public abstract boolean shouldStart();

    public boolean shouldFinish() {
        return !shouldStart();
    }

    public void start() {
    }

    public void finish() {
    }

    public abstract void tick();
}