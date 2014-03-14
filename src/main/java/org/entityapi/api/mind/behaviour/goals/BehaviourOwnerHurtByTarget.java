package org.entityapi.api.mind.behaviour.goals;

import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.behaviour.BehaviourType;

public class BehaviourOwnerHurtByTarget extends BehaviourTarget {

    public BehaviourOwnerHurtByTarget(ControllableEntity controllableEntity, boolean checkSenses) {
        super(controllableEntity, checkSenses);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ONE;
    }

    @Override
    public String getDefaultKey() {
        return "Owner Hurt By Target";
    }

    @Override
    public boolean shouldStart() {
        return false;
    }

    @Override
    public void tick() {

    }
}