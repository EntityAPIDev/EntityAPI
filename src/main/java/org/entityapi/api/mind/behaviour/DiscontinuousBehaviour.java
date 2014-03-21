package org.entityapi.api.mind.behaviour;

import org.entityapi.api.ControllableEntity;

public abstract class DiscontinuousBehaviour extends Behaviour {

    protected DiscontinuousBehaviour(ControllableEntity controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public boolean isContinuous() {
        return false;
    }
}