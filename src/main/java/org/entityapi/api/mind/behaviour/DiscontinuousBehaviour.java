package org.entityapi.api.mind.behaviour;

public abstract class DiscontinuousBehaviour extends Behaviour {

    @Override
    public boolean isContinuous() {
        return false;
    }
}