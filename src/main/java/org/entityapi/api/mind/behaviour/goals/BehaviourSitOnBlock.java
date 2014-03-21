package org.entityapi.api.mind.behaviour.goals;

import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.behaviour.Behaviour;
import org.entityapi.api.mind.behaviour.BehaviourType;

public class BehaviourSitOnBlock extends Behaviour {

    public BehaviourSitOnBlock(ControllableEntity controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.FIVE;
    }

    @Override
    public String getDefaultKey() {
        return "Sit On Block";
    }

    @Override
    public boolean shouldStart() {
        return false;
    }

    @Override
    public void tick() {

    }
}