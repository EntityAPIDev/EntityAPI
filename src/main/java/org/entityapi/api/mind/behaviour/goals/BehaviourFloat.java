package org.entityapi.api.mind.behaviour.goals;

import org.entityapi.api.mind.behaviour.Behaviour;
import org.entityapi.api.mind.behaviour.BehaviourType;

public class BehaviourFloat extends Behaviour {

    @Override
    public BehaviourType getType() {
        return BehaviourType.FOUR;
    }

    @Override
    public String getDefaultKey() {
        return "Float";
    }

    @Override
    public boolean shouldStart() {
        return false;
    }

    @Override
    public void tick() {

    }
}