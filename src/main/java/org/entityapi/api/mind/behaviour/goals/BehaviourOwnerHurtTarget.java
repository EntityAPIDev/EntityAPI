package org.entityapi.api.mind.behaviour.goals;

import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.behaviour.BehaviourType;

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