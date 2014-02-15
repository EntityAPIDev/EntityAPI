package io.snw.entityapi.entity.selector;

import io.snw.entityapi.api.mind.behaviour.goals.BehaviourAvoidEntity;
import io.snw.entityapi.nms.NMSEntityUtil;
import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.IEntitySelector;

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