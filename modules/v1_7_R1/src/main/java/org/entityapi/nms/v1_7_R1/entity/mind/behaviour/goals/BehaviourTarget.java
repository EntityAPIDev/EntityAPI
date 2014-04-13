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
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.event.entity.EntityTargetEvent;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;

/**
 * Base target Behaviour
 */

public abstract class BehaviourTarget extends BehaviourBase {

    private boolean checkSenses;
    private boolean useMelee;
    private int shouldAttack;
    private int ticksAfterLastAttack;
    private int targetNotVisibleTicks;

    public BehaviourTarget(ControllableEntity controllableEntity, boolean checkSenses) {
        this(controllableEntity, checkSenses, false);
    }

    public BehaviourTarget(ControllableEntity controllableEntity, boolean checkSenses, boolean useMelee) {
        super(controllableEntity);
        this.checkSenses = checkSenses;
        this.useMelee = useMelee;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.SUBCONSCIOUS;
    }

    @Override
    public String getDefaultKey() {
        return "Target";
    }

    @Override
    public boolean shouldContinue() {
        EntityLiving entityliving = ((CraftLivingEntity) this.getControllableEntity().getTarget()).getHandle();

        if (entityliving == null) {
            return false;
        } else if (!entityliving.isAlive()) {
            return false;
        } else {
            double range = this.getControllableEntity().getPathfindingRange();

            if (this.getHandle().e(entityliving) > range * range) {
                return false;
            } else {
                if (this.checkSenses) {
                    if (NMSEntityUtil.getEntitySenses(this.getHandle()).canSee(entityliving)) {
                        this.targetNotVisibleTicks = 0;
                    } else if (++this.targetNotVisibleTicks > 60) {
                        return false;
                    }
                }

                return !(entityliving instanceof EntityPlayer) || !((EntityPlayer) entityliving).playerInteractManager.isCreative();
            }
        }
    }

    @Override
    public void start() {
        this.shouldAttack = 0;
        this.ticksAfterLastAttack = 0;
        this.targetNotVisibleTicks = 0;
    }

    @Override
    public void finish() {
        this.getControllableEntity().setTarget(null);
    }

    public boolean isSuitableTarget(EntityLiving entityliving, boolean flag) {
        if (entityliving == null) {
            return false;
        } else if (entityliving == this.getHandle()) {
            return false;
        } else if (!entityliving.isAlive()) {
            return false;
        } else if (!this.canAttackClass(entityliving.getClass())) {
            return false;
        } else {
            if (this.getHandle() instanceof EntityOwnable && StringUtils.isNotEmpty(((EntityOwnable) this.getHandle()).getOwnerName())) {
                if (entityliving instanceof EntityOwnable && ((EntityOwnable) this.getHandle()).getOwnerName().equals(((EntityOwnable) entityliving).getOwnerName())) {
                    return false;
                }

                if (entityliving == ((EntityOwnable) this.getHandle()).getOwner()) {
                    return false;
                }
            } else if (entityliving instanceof EntityHuman && !flag && ((EntityHuman) entityliving).abilities.isInvulnerable) {
                return false;
            }

            if (!NMSEntityUtil.isInHomeArea(this.getHandle(), MathHelper.floor(entityliving.locX), MathHelper.floor(entityliving.locY), MathHelper.floor(entityliving.locZ))) {
                return false;
            } else if (this.checkSenses && !NMSEntityUtil.getEntitySenses(this.getHandle()).canSee(entityliving)) {
                return false;
            } else {
                if (this.useMelee) {
                    if (--this.ticksAfterLastAttack <= 0) {
                        this.shouldAttack = 0;
                    }

                    if (this.shouldAttack == 0) {
                        this.shouldAttack = this.attack(entityliving) ? 1 : 2;
                    }

                    if (this.shouldAttack == 2) {
                        return false;
                    }
                }

                // CraftBukkit start - Check all the different target goals for the reason, default to RANDOM_TARGET
                EntityTargetEvent.TargetReason reason = EntityTargetEvent.TargetReason.RANDOM_TARGET;

                if (this instanceof BehaviourDefendVillage) {
                    reason = EntityTargetEvent.TargetReason.DEFEND_VILLAGE;
                } else if (this instanceof BehaviourHurtByTarget) {
                    reason = EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY;
                } else if (this instanceof BehaviourMoveTowardsNearestAttackableTarget) {
                    if (entityliving instanceof EntityHuman) {
                        reason = EntityTargetEvent.TargetReason.CLOSEST_PLAYER;
                    }
                } else if (this instanceof BehaviourDefendTamer) {
                    reason = EntityTargetEvent.TargetReason.TARGET_ATTACKED_OWNER;
                } else if (this instanceof BehaviourHelpTamerTarget) {
                    reason = EntityTargetEvent.TargetReason.OWNER_ATTACKED_TARGET;
                }

                org.bukkit.event.entity.EntityTargetLivingEntityEvent event = org.bukkit.craftbukkit.v1_7_R1.event.CraftEventFactory.callEntityTargetLivingEvent(this.getHandle(), entityliving, reason);
                if (event.isCancelled() || event.getTarget() == null) {
                    this.getControllableEntity().setTarget(null);
                    return false;
                } else if (entityliving.getBukkitEntity() != event.getTarget()) {
                    this.getControllableEntity().setTarget(event.getTarget());
                }
                if (this.getHandle() instanceof EntityCreature) {
                    ((EntityCreature) this.getHandle()).target = ((CraftEntity) event.getTarget()).getHandle();
                }
                // CraftBukkit end

                return true;
            }
        }
    }

    private boolean attack(EntityLiving entityliving) {
        this.ticksAfterLastAttack = 10 + this.getHandle().aI().nextInt(5);
        PathEntity pathentity = NMSEntityUtil.getNavigation(this.getHandle()).a(entityliving);

        if (pathentity == null) {
            return false;
        } else {
            PathPoint pathpoint = pathentity.c();

            if (pathpoint == null) {
                return false;
            } else {
                int i = pathpoint.a - MathHelper.floor(entityliving.locX);
                int j = pathpoint.c - MathHelper.floor(entityliving.locZ);

                return (double) (i * i + j * j) <= 2.25D;
            }
        }
    }

    private boolean canAttackClass(Class oclass) {
        if (this.getHandle() instanceof EntityInsentient) {
            return ((EntityInsentient) this.getHandle()).a(oclass);
        } else {
            return EntityCreeper.class != oclass && EntityGhast.class != oclass;
        }
    }
}