package org.entityapi.entity.selector;

import org.entityapi.api.mind.behaviour.goals.BehaviourMoveTowardsNearestAttackableTarget;
import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.EntityLiving;
import net.minecraft.server.v1_7_R1.IEntitySelector;

public class EntitySelectorNearestAttackableTarget implements IEntitySelector {

    final IEntitySelector selector;

    final BehaviourMoveTowardsNearestAttackableTarget behaviour;

    public EntitySelectorNearestAttackableTarget(BehaviourMoveTowardsNearestAttackableTarget behaviour, IEntitySelector ientityselector) {
        this.behaviour = behaviour;
        this.selector = ientityselector;
    }

    @Override
    public boolean a(Entity entity) {
        return !(entity instanceof EntityLiving) ? false : (this.selector != null && !this.selector.a(entity) ? false : this.behaviour.isSuitableTarget((EntityLiving) entity, false));
    }
}