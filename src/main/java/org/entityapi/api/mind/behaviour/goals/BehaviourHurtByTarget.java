package org.entityapi.api.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.AxisAlignedBB;
import net.minecraft.server.v1_7_R1.EntityCreature;
import org.bukkit.entity.LivingEntity;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.behaviour.BehaviourType;

import java.util.Iterator;
import java.util.List;

public class BehaviourHurtByTarget extends BehaviourTarget {

    private boolean attackNearest;
    private int lastAttackTick;

    public BehaviourHurtByTarget(ControllableEntity controllableEntity, boolean attackNearest) {
        this(controllableEntity, false, false, attackNearest);
    }

    public BehaviourHurtByTarget(ControllableEntity controllableEntity, boolean checkSenses, boolean useMelee, boolean attackNearest) {
        super(controllableEntity, checkSenses, useMelee);
        this.attackNearest = attackNearest;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ONE;
    }

    @Override
    public String getDefaultKey() {
        return "Hurt By Target";
    }

    @Override
    public boolean shouldStart() {
        int lastAttackTick = this.handle.aK();

        return lastAttackTick != this.lastAttackTick && this.isSuitableTarget(this.handle.getLastDamager(), false);
    }

    @Override
    public void tick() {
        this.controllableEntity.setTarget((LivingEntity) this.handle.getLastDamager().getBukkitEntity());
        this.lastAttackTick = this.handle.aK();
        if (this.attackNearest) {
            double range = this.controllableEntity.getPathfindingRange();
            List list = this.handle.world.a(this.handle.getClass(), AxisAlignedBB.a().a(this.handle.locX, this.handle.locY, this.handle.locZ, this.handle.locX + 1.0D, this.handle.locY + 1.0D, this.handle.locZ + 1.0D).grow(range, 10.0D, range));
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityCreature entitycreature = (EntityCreature) iterator.next();

                if (this.handle != entitycreature && entitycreature.getGoalTarget() == null && !entitycreature.c(this.handle.getLastDamager())) {
                    entitycreature.setGoalTarget(this.handle.getLastDamager());
                }
            }
        }

        super.tick();
    }
}