package org.entityapi.api.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.EntityCreeper;
import net.minecraft.server.v1_7_R1.EntityLiving;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Creeper;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.behaviour.Behaviour;
import org.entityapi.api.mind.behaviour.BehaviourType;

public class BehaviourSwell extends Behaviour {

    private EntityLiving target;

    public BehaviourSwell(ControllableEntity<Creeper, EntityCreeper> controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public ControllableEntity<Creeper, EntityCreeper> getControllableEntity() {
        return super.getControllableEntity();
    }

    @Override
    public EntityCreeper getHandle() {
        return this.getControllableEntity().getHandle();
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ONE;
    }

    @Override
    public String getDefaultKey() {
        return "Swell";
    }

    @Override
    public boolean shouldStart() {
        EntityLiving entityliving = ((CraftLivingEntity) this.getControllableEntity().getTarget()).getHandle();
        return this.getHandle().bZ() > 0 || entityliving != null && this.getHandle().e(entityliving) < 9.0D;
    }

    @Override
    public void start() {
        this.getHandle().getNavigation().h();
        this.target = this.getHandle().getGoalTarget();
    }

    @Override
    public void finish() {
        this.target = null;
    }

    @Override
    public void tick() {
        if (this.target == null) {
            this.getHandle().a(-1);
        } else if (this.getHandle().e(this.target) > 49.0D) {
            this.getHandle().a(-1);
        } else if (!this.getHandle().getEntitySenses().canSee(this.target)) {
            this.getHandle().a(-1);
        } else {
            this.getHandle().a(1);
        }
    }
}