/*
 * Copyright (C) EntityAPI Team
 *
 * This file is part of EntityAPI.
 *
 * EntityAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EntityAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EntityAPI.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.entityapi.nms.v1_8_R1.entity.mind.behaviour.goals;

import net.minecraft.server.v1_8_R1.EntityLiving;
import net.minecraft.server.v1_8_R1.MathHelper;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R1.event.CraftEventFactory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityTargetEvent;
import org.entityapi.api.ProjectileType;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_8_R1.NMSEntityUtil;
import org.entityapi.nms.v1_8_R1.entity.mind.behaviour.BehaviourGoalBase;

/**
 * Using this causes the entity to attack targets from a certain range
 */

public class BehaviourGoalRangedAttack extends BehaviourGoalBase {

    private ProjectileType projectileType;
    private int minDelay;
    private int maxDelay;
    private float range;
    private float rangeSquared;
    private int inRangeTicks;
    private int shootCooldown;
    private double navigationSpeed;

    private EntityLiving target;

    public BehaviourGoalRangedAttack(ControllableEntity controllableEntity, ProjectileType projectileType, int minDelay, int maxDelay, float range, double navigationSpeed) {
        super(controllableEntity);
        this.projectileType = projectileType;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.range = range;
        this.rangeSquared = range * range;
        this.navigationSpeed = navigationSpeed;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ACTION;
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
            this.getControllableEntity().navigateTo((LivingEntity) this.target.getBukkitEntity(), this.navigationSpeed > 0 ? this.navigationSpeed : this.getControllableEntity().getSpeed());
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