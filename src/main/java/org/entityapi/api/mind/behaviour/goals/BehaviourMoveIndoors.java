package org.entityapi.api.mind.behaviour.goals;

import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.behaviour.Behaviour;
import org.entityapi.api.mind.behaviour.BehaviourType;

public class BehaviourMoveIndoors extends Behaviour {

    public BehaviourMoveIndoors(ControllableEntity controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ONE;
    }

    @Override
    public String getDefaultKey() {
        return "Move Indoors";
    }

    @Override
    public boolean shouldStart() {
        return false;
    }

    @Override
    public void tick() {

    }
}