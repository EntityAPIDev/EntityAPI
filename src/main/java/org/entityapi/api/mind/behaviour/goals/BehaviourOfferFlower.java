package org.entityapi.api.mind.behaviour.goals;

import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.behaviour.Behaviour;
import org.entityapi.api.mind.behaviour.BehaviourType;

public class BehaviourOfferFlower extends Behaviour {

    public BehaviourOfferFlower(ControllableEntity controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.THREE;
    }

    @Override
    public String getDefaultKey() {
        return "Offer Flower";
    }

    @Override
    public boolean shouldStart() {
        return false;
    }

    @Override
    public void tick() {

    }
}