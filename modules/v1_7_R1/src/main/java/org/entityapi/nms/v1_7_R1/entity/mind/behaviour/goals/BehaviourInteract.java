package org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals;

import org.bukkit.entity.Entity;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.BehaviourType;

public class BehaviourInteract extends BehaviourLookAtNearestEntity {

    public BehaviourInteract(ControllableEntity controllableEntity, Class<? extends Entity> classType, float minDistance) {
        super(controllableEntity, classType, minDistance);
    }

    public BehaviourInteract(ControllableEntity controllableEntity, Class<? extends Entity> classType, float minDistance, float chance) {
        super(controllableEntity, classType, minDistance, chance);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.THREE;
    }

    @Override
    public String getDefaultKey() {
        return "Interact";
    }
}