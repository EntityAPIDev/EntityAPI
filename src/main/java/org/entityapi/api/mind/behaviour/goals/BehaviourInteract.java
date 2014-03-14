package org.entityapi.api.mind.behaviour.goals;

import org.entityapi.api.mind.behaviour.BehaviourType;

public class BehaviourInteract extends BehaviourLookAtNearestEntity {

    @Override
    public BehaviourType getType() {
        return BehaviourType.THREE;
    }

    @Override
    public String getDefaultKey() {
        return "Interact";
    }

    @Override
    public boolean shouldStart() {
        return false;
    }

    @Override
    public void tick() {

    }
}