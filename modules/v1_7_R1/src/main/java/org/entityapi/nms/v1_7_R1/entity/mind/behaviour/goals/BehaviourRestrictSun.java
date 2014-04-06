package org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals;

import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.BehaviourType;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;

public class BehaviourRestrictSun extends BehaviourBase {

    public BehaviourRestrictSun(ControllableEntity controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ZERO;
    }

    @Override
    public String getDefaultKey() {
        return "Restrict Sun";
    }

    @Override
    public boolean shouldStart() {
        return false;
    }

    @Override
    public void tick() {

    }
}