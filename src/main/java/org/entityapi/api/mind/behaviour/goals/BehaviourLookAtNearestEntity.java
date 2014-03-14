package org.entityapi.api.mind.behaviour.goals;

import org.entityapi.api.mind.behaviour.Behaviour;
import org.entityapi.api.mind.behaviour.BehaviourType;

public class BehaviourLookAtNearestEntity extends Behaviour {

    @Override
    public BehaviourType getType() {
        return BehaviourType.TWO;
    }

    @Override
    public String getDefaultKey() {
        return "Look Nearest Entity";
    }

    @Override
    public boolean shouldStart() {
        return false;
    }

    @Override
    public void tick() {

    }
}