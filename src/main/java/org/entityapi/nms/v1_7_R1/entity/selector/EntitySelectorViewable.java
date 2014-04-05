package org.entityapi.nms.v1_7_R1.entity.selector;

import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.IEntitySelector;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals.BehaviourAvoidEntity;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;

public class EntitySelectorViewable implements IEntitySelector {

    final BehaviourAvoidEntity c;

    public EntitySelectorViewable(BehaviourAvoidEntity behaviourAvoidEntity) {
        this.c = behaviourAvoidEntity;
    }

    @Override
    public boolean a(Entity entity) {
        return entity.isAlive() && NMSEntityUtil.getEntitySenses(BehaviourAvoidEntity.getEntityFor(this.c).getBukkitEntity()).canSee(entity);
    }
}