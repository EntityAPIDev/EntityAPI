package org.entityapi.api.mind.behaviour.goals;

import org.entityapi.api.ControllableEntity;

public class BehaviourOpenDoor extends BehaviourDoorInteract {

    public BehaviourOpenDoor(ControllableEntity controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public String getDefaultKey() {
        return "Open Door";
    }
}