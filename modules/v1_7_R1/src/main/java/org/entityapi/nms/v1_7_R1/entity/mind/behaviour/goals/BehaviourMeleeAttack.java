/*
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

package org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.*;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityTargetEvent;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.ControllableBaseEntity;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;

public class BehaviourMeleeAttack extends BehaviourBase {

    private int attackTicks;
    private boolean ignoreSight;
    private PathEntity pathToAttack;
    private Class typeToAttack;
    private int moveTicks;
    private double targetX;
    private double targetY;
    private double targetZ;

    public BehaviourMeleeAttack(ControllableEntity controllableEntity, Class typeToAttack, boolean ignoreSight) {
        this(controllableEntity, ignoreSight);
        this.typeToAttack = typeToAttack;
    }

    public BehaviourMeleeAttack(ControllableEntity controllableEntity, boolean ignoreSight) {
        super(controllableEntity);
        this.ignoreSight = ignoreSight;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.THREE;
    }

    @Override
    public String getDefaultKey() {
        return "Melee Attack";
    }

    @Override
    public boolean shouldStart() {
        if (this.getControllableEntity().getTarget() == null) {
            return false;
        }

        EntityLiving targetHandle = ((CraftLivingEntity) this.getControllableEntity().getTarget()).getHandle();

        if (!targetHandle.isAlive()) {
            return false;
        } else if (this.typeToAttack != null && !this.typeToAttack.isAssignableFrom(targetHandle.getClass())) {
            return false;
        } else {
            this.pathToAttack = NMSEntityUtil.getNavigation(this.getHandle()).a(targetHandle);
            return this.pathToAttack != null;
        }
    }

    @Override
    public boolean shouldContinue() {
        LivingEntity target = this.getControllableEntity().getTarget();

        // CraftBukkit start
        EntityTargetEvent.TargetReason reason = this.getControllableEntity().getTarget() == null ? EntityTargetEvent.TargetReason.FORGOT_TARGET : EntityTargetEvent.TargetReason.TARGET_DIED;
        if (this.getControllableEntity().getTarget() == null || (this.getControllableEntity().getTarget() != null && !((CraftLivingEntity) this.getControllableEntity().getTarget()).getHandle().isAlive())) {
            org.bukkit.craftbukkit.v1_7_R1.event.CraftEventFactory.callEntityTargetEvent(this.getHandle(), null, reason);
        }
        // CraftBukkit end

        if (target == null) {
            return false;
        }
        EntityLiving targetHandle = ((CraftLivingEntity) target).getHandle();
        return !targetHandle.isAlive() ? false : (!this.ignoreSight ? !NMSEntityUtil.getNavigation(this.getHandle()).g() : NMSEntityUtil.isInGuardedAreaOf(this.getHandle(), MathHelper.floor(targetHandle.locX), MathHelper.floor(targetHandle.locY), MathHelper.floor(targetHandle.locZ)));
    }

    @Override
    public void start() {
        ((ControllableBaseEntity) this.getControllableEntity()).navigateTo(this.pathToAttack);
        this.moveTicks = 0;
    }

    @Override
    public void finish() {
        NMSEntityUtil.getNavigation(this.getHandle()).h();
    }

    @Override
    public void tick() {
        EntityLiving target = ((CraftLivingEntity) this.getControllableEntity().getTarget()).getHandle();

        NMSEntityUtil.getControllerLook(this.getHandle()).a(target, 30.0F, 30.0F);
        double dist = this.getHandle().e(target.locX, target.boundingBox.b, target.locZ);
        double minDist = (double) (this.getHandle().width * 2.0F * this.getHandle().width * 2.0F + target.width);

        --this.moveTicks;
        if ((this.ignoreSight || NMSEntityUtil.getEntitySenses(this.getHandle()).canSee(target)) && this.moveTicks <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || target.e(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.getHandle().aI().nextFloat() < 0.05F)) {
            this.targetX = target.locX;
            this.targetY = target.boundingBox.b;
            this.targetZ = target.locZ;

            this.moveTicks = 4 + this.getHandle().aI().nextInt(7);
            if (dist > 1024.0D) {
                this.moveTicks += 10;
            } else if (dist > 256.0D) {
                this.moveTicks += 5;
            }

            if (!((ControllableBaseEntity) this.getControllableEntity()).navigateTo(this.pathToAttack)) {
                this.moveTicks += 15;
            }
        }

        this.attackTicks = Math.max(this.attackTicks - 1, 0);
        if (dist <= minDist && this.attackTicks <= 20) {
            this.attackTicks = 20;
            if (this.getHandle().be() != null) {
                this.getHandle().ba();
            }

            this.getHandle().m(target);
        }
    }
}