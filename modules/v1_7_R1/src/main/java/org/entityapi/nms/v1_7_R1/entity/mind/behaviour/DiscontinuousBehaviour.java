package org.entityapi.nms.v1_7_R1.entity.mind.behaviour;

import org.entityapi.api.ControllableEntity;

public abstract class DiscontinuousBehaviour extends BehaviourBase {

    protected DiscontinuousBehaviour(ControllableEntity controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public boolean isContinuous() {
        return false;
    }
}