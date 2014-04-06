package org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals;

import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.BehaviourType;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;

public class BehaviourFloat extends BehaviourBase {

    public BehaviourFloat(ControllableEntity controllableEntity) {
        super(controllableEntity);
        NMSEntityUtil.getNavigation(this.getHandle()).e(true);
    }

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
        return this.getHandle().M() || this.getHandle().P();
    }

    @Override
    public void tick() {
        if (this.getHandle().aI().nextFloat() < 0.8F) {
            NMSEntityUtil.getControllerJump(this.getHandle()).a();
        }
    }
}