package org.entityapi.api.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.EntityLiving;
import net.minecraft.server.v1_7_R1.MathHelper;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_7_R1.event.CraftEventFactory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityTargetEvent;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.ProjectileType;
import org.entityapi.api.mind.behaviour.Behaviour;
import org.entityapi.api.mind.behaviour.BehaviourType;
import org.entityapi.nms.NMSEntityUtil;

/**
 * Using this causes the entity to attack targets from a certain range
 */

public class BehaviourRangedAttack extends Behaviour {

    private ProjectileType projectileType;
    private int minDelay;
    private int maxDelay;
    private float range;
    private float rangeSquared;
    private int inRangeTicks;
    private int shootCooldown;

    private EntityLiving target;

    public BehaviourRangedAttack(ControllableEntity controllableEntity, int minDelay, float range) {
        this(controllableEntity, ProjectileType.DEFAULT, minDelay, range);
    }

    public BehaviourRangedAttack(ControllableEntity controllableEntity, int minDelay, int maxDelay, float range) {
        this(controllableEntity, ProjectileType.DEFAULT, minDelay, maxDelay, range);
    }

    public BehaviourRangedAttack(ControllableEntity controllableEntity, ProjectileType projectileType, int minDelay, float range) {
        this(controllableEntity, projectileType, minDelay, minDelay, range);
    }

    public BehaviourRangedAttack(ControllableEntity controllableEntity, ProjectileType projectileType, int minDelay, int maxDelay, float range) {
        super(controllableEntity);
        this.projectileType = projectileType;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.range = range;
        this.rangeSquared = range * range;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.THREE;
    }

    @Override
    public String getDefaultKey() {
        return "Ranged Attack";
    }

    @Override
    public boolean shouldStart() {
        EntityLiving entityliving = ((CraftLivingEntity) this.getControllableEntity().getTarget()).getHandle();

        if (entityliving == null) {
            return false;
        } else {
            this.target = entityliving;
            return true;
        }
    }

    @Override
    public boolean shouldContinue() {
        return this.shouldStart() || !NMSEntityUtil.getNavigation(this.getControllableEntity().getBukkitEntity()).g();
    }

    @Override
    public void finish() {
        // CraftBukkit start
        EntityTargetEvent.TargetReason reason = this.target.isAlive() ? EntityTargetEvent.TargetReason.FORGOT_TARGET : EntityTargetEvent.TargetReason.TARGET_DIED;
        CraftEventFactory.callEntityTargetEvent(this.getHandle(), null, reason);
        // CraftBukkit end
        this.target = null;
        this.inRangeTicks = 0;
        this.shootCooldown = -1;
    }

    @Override
    public void tick() {
        double distanceToTarget = this.getHandle().e(this.target.locX, this.target.boundingBox.b, this.target.locZ);
        boolean canSee = NMSEntityUtil.getEntitySenses(this.getHandle()).canSee(this.target);

        if (canSee) {
            ++this.inRangeTicks;
        } else {
            this.inRangeTicks = 0;
        }

        if (distanceToTarget <= (double) this.rangeSquared && this.inRangeTicks >= 20) {
            NMSEntityUtil.getNavigation(this.getHandle()).h();
        } else {
            this.getControllableEntity().navigateTo((LivingEntity) this.target.getBukkitEntity());
        }

        NMSEntityUtil.getControllerLook(this.getHandle()).a(this.target, 30.0F, 30.0F);
        float strength;

        if (--this.shootCooldown == 0) {
            if (distanceToTarget > (double) this.rangeSquared || !canSee) {
                return;
            }

            strength = MathHelper.sqrt(distanceToTarget) / this.range;
            float finalStrength = strength;

            if (strength < 0.1F) {
                finalStrength = 0.1F;
            }

            if (finalStrength > 1.0F) {
                finalStrength = 1.0F;
            }

            this.projectileType.shootProjectile(this.getControllableEntity(), (LivingEntity) this.target.getBukkitEntity(), finalStrength);
            this.shootCooldown = MathHelper.d(strength * (float) (this.maxDelay - this.minDelay) + (float) this.minDelay);
        } else if (this.shootCooldown < 0) {
            strength = MathHelper.sqrt(distanceToTarget) / this.range;
            this.shootCooldown = MathHelper.d(strength * (float) (this.maxDelay - this.minDelay) + (float) this.minDelay);
        }
    }

    private void shootProjectile(float strength) {
        this.projectileType.shootProjectile(this.getControllableEntity(), (LivingEntity) this.target.getBukkitEntity(), strength);
    }
}