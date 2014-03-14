package org.entityapi.api.mind.behaviour.goals;

import org.entityapi.api.mind.behaviour.BehaviourType;
import org.entityapi.api.mind.behaviour.DiscontinuousBehaviour;

public class BehaviourMoveTowardsLocation extends DiscontinuousBehaviour {

    @Override
    public BehaviourType getType() {
        return BehaviourType.ONE;
    }

    @Override
    public String getDefaultKey() {
        return "Move Towards Location";
    }

    @Override
    public boolean shouldStart() {
        return false;
    }

    @Override
    public void tick() {

    }
}