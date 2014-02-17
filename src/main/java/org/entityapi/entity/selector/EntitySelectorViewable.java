package org.entityapi.entity.selector;

import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.IEntitySelector;
import org.entityapi.api.mind.behaviour.goals.BehaviourAvoidEntity;
import org.entityapi.nms.NMSEntityUtil;

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