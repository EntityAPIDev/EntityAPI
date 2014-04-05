package org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals;

import org.entityapi.api.ControllableEntity;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;
import org.entityapi.api.mind.BehaviourType;

public class BehaviourFollowCarrotStick extends BehaviourBase {

    public BehaviourFollowCarrotStick(ControllableEntity controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.SEVEN;
    }

    @Override
    public String getDefaultKey() {
        return "Follow Carrot";
    }

    @Override
    public boolean shouldStart() {
        return false;
    }

    @Override
    public void tick() {

    }
}