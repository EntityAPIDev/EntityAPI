package io.snw.entityapi.entity;

import io.snw.entityapi.api.Attacking;
import io.snw.entityapi.api.ControllableEntityType;
import net.minecraft.server.v1_7_R1.EntityInsentient;
import net.minecraft.server.v1_7_R1.EntityLiving;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

public class ControllableAttackingBaseEntity<T extends LivingEntity> extends ControllableBaseEntity<T> implements Attacking {

    public ControllableAttackingBaseEntity(ControllableEntityType entityType) {
        super(entityType);
    }

    @Override
    public LivingEntity getTarget() {
        if (this.handle == null) {
            return null;
        }

        if (this.handle instanceof EntityInsentient) {
            EntityLiving targetHandle = ((EntityInsentient) this.handle).getGoalTarget();
            if (targetHandle != null && targetHandle.getBukkitEntity() instanceof LivingEntity) {
                return (LivingEntity) targetHandle.getBukkitEntity();
            }
        }

        return null;
    }

    @Override
    public void setTarget(LivingEntity target) {
        if (this.handle == null) {
            return;
        }

        if (this.handle instanceof EntityInsentient) {
            ((EntityInsentient) this.handle).setGoalTarget(((CraftLivingEntity) target).getHandle());
        }
    }
}