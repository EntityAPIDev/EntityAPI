package org.entityapi.api.mind.behaviour.goals;

import org.entityapi.api.ControllableEntity;
import org.entityapi.api.ProjectileType;
import org.entityapi.api.mind.behaviour.Behaviour;
import org.entityapi.api.mind.behaviour.BehaviourType;
import org.entityapi.nms.NMSEntityUtil;
import net.minecraft.server.v1_7_R1.*;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

/**
 * Using this causes the entity to attack targets from a certain range
 */

public class BehaviourRangedAttack extends Behaviour {

    private ControllableEntity controllableEntity;
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
        this.controllableEntity = controllableEntity;
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
        EntityLiving entityliving = ((CraftLivingEntity) this.controllableEntity.getTarget()).getHandle();

        if (entityliving == null) {
            return false;
        } else {
            this.target = entityliving;
            return true;
        }
    }

    @Override
    public boolean shouldContinue() {
        return this.shouldStart() || !NMSEntityUtil.getNavigation(this.controllableEntity.getBukkitEntity()).g();
    }

    @Override
    public void finish() {
        this.target = null;
        this.inRangeTicks = 0;
        this.shootCooldown = -1;
    }

    @Override
    public void tick() {
        double distanceToTarget = this.controllableEntity.getHandle().e(this.target.locX, this.target.boundingBox.b, this.target.locZ);
        boolean canSee = NMSEntityUtil.getEntitySenses(this.controllableEntity.getHandle()).canSee(this.target);

        if (canSee) {
            ++this.inRangeTicks;
        } else {
            this.inRangeTicks = 0;
        }

        if (distanceToTarget <= (double) this.rangeSquared && this.inRangeTicks >= 20) {
            NMSEntityUtil.getNavigation(this.controllableEntity.getHandle()).h();
        } else {
            this.controllableEntity.navigateTo((LivingEntity) this.target.getBukkitEntity());
        }

        NMSEntityUtil.getControllerLook(this.controllableEntity.getHandle()).a(this.target, 30.0F, 30.0F);
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

            this.projectileType.shootProjectile(this.controllableEntity, (LivingEntity) this.target.getBukkitEntity(), finalStrength);
            this.shootCooldown = MathHelper.d(strength * (float) (this.maxDelay - this.minDelay) + (float) this.minDelay);
        } else if (this.shootCooldown < 0) {
            strength = MathHelper.sqrt(distanceToTarget) / this.range;
            this.shootCooldown = MathHelper.d(strength * (float) (this.maxDelay - this.minDelay) + (float) this.minDelay);
        }
    }

    private void shootProjectile(float strength) {
        this.projectileType.shootProjectile(this.controllableEntity, (LivingEntity) this.target.getBukkitEntity(), strength);
    }
}