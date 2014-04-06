package org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals;

import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.BehaviourType;

public class BehaviourOwnerHurtTarget extends BehaviourTarget {

    public BehaviourOwnerHurtTarget(ControllableEntity controllableEntity, boolean checkSenses) {
        super(controllableEntity, checkSenses);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ONE;
    }

    @Override
    public String getDefaultKey() {
        return "Owner Hurt Target";
    }

    @Override
    public boolean shouldStart() {
        return false;
    }

    @Override
    public void tick() {

    }
}