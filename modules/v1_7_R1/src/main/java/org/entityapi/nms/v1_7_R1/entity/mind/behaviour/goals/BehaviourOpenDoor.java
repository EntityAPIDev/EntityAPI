package org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals;

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