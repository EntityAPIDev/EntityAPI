package org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.EntityHuman;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.entityapi.api.ControllableEntity;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;
import org.entityapi.api.mind.BehaviourType;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.reflection.refs.NMSEntityClassRef;

public class BehaviourLookAtNearestEntity extends BehaviourBase {

    protected Entity target;
    private float minDist;
    private int ticks;
    private float chance;
    private Class<? extends Entity> entityClass;

    public BehaviourLookAtNearestEntity(ControllableEntity controllableEntity, Class<? extends org.bukkit.entity.Entity> classType, float minDistance) {
        this(controllableEntity, classType, minDistance, 0.02F);
    }

    public BehaviourLookAtNearestEntity(ControllableEntity controllableEntity, Class<? extends org.bukkit.entity.Entity> classType, float minDistance, float chance) {
        super(controllableEntity);
        this.entityClass = (Class<? extends Entity>) NMSEntityClassRef.getNMSClass(classType);
        this.minDist = minDistance;
        this.chance = chance;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.TWO;
    }

    @Override
    public String getDefaultKey() {
        return "Look Nearest Entity";
    }

    @Override
    public boolean shouldStart() {
        if (this.getHandle().aI().nextFloat() >= this.chance) {
            return false;
        } else {
            if (this.getControllableEntity().getTarget() != null) {
                this.target = ((CraftLivingEntity) this.getControllableEntity().getTarget()).getHandle();
            }

            if (this.entityClass == EntityHuman.class) {
                this.target = this.getHandle().world.findNearbyPlayer(this.getHandle(), (double) this.minDist);
            } else {
                this.target = this.getHandle().world.a(this.entityClass, this.getHandle().boundingBox.grow((double) this.minDist, 3.0D, (double) this.minDist), this.getHandle());
            }

            return this.target != null;
        }
    }

    @Override
    public boolean shouldContinue() {
        return !this.target.isAlive() ? false : (this.getHandle().e(this.target) > (double) (this.minDist * this.minDist) ? false : this.ticks > 0);
    }

    @Override
    public void start() {
        this.ticks = 40 + this.getHandle().aI().nextInt(40);
    }

    @Override
    public void finish() {
        this.target = null;
    }

    @Override
    public void tick() {
        NMSEntityUtil.getControllerLook(this.getHandle()).a(this.target.locX, this.target.locY + (double) this.target.getHeadHeight(), this.target.locZ, 10.0F, (float) NMSEntityUtil.getMaxHeadRotation(this.getHandle()));
        --this.ticks;
    }
}