package org.entityapi.api.mind.behaviour.goals;

import org.entityapi.api.mind.behaviour.Behaviour;
import org.entityapi.api.mind.behaviour.BehaviourType;

public class BehaviourEatGrass extends Behaviour {

    @Override
    public BehaviourType getType() {
        return BehaviourType.SEVEN;
    }

    @Override
    public String getDefaultKey() {
        return "Eat Grass";
    }

    @Override
    public boolean shouldStart() {
        return false;
    }

    @Override
    public void tick() {

    }
}