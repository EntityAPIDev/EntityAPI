package org.entityapi.api.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.*;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.event.entity.EntityTargetEvent;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.behaviour.Behaviour;
import org.entityapi.api.mind.behaviour.BehaviourType;
import org.entityapi.nms.NMSEntityUtil;

/**
 * Base target Behaviour
 */

public abstract class BehaviourTarget extends Behaviour {

    protected ControllableEntity controllableEntity;
    protected EntityLiving handle;
    private boolean checkSenses;
    private boolean useMelee;
    private int shouldAttack;
    private int ticksAfterLastAttack;
    private int targetNotVisibleTicks;

    public BehaviourTarget(ControllableEntity controllableEntity, boolean checkSenses) {
        this(controllableEntity, checkSenses, false);
    }

    public BehaviourTarget(ControllableEntity controllableEntity, boolean checkSenses, boolean useMelee) {
        this.controllableEntity = controllableEntity;
        this.handle = controllableEntity.getHandle();
        this.checkSenses = checkSenses;
        this.useMelee = useMelee;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ZERO;
    }

    @Override
    public String getDefaultKey() {
        return "Target";
    }

    @Override
    public boolean shouldContinue() {
        EntityLiving entityliving = ((CraftLivingEntity) this.controllableEntity.getTarget()).getHandle();

        if (entityliving == null) {
            return false;
        } else if (!entityliving.isAlive()) {
            return false;
        } else {
            double range = this.controllableEntity.getPathfindingRange();

            if (this.handle.e(entityliving) > range * range) {
                return false;
            } else {
                if (this.checkSenses) {
                    if (NMSEntityUtil.getEntitySenses(this.handle).canSee(entityliving)) {
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
        this.controllableEntity.setTarget(null);
    }

    public boolean isSuitableTarget(EntityLiving entityliving, boolean flag) {
        if (entityliving == null) {
            return false;
        } else if (entityliving == this.handle) {
            return false;
        } else if (!entityliving.isAlive()) {
            return false;
        } else if (!this.canAttackClass(entityliving.getClass())) {
            return false;
        } else {
            if (this.handle instanceof EntityOwnable && StringUtils.isNotEmpty(((EntityOwnable) this.handle).getOwnerName())) {
                if (entityliving instanceof EntityOwnable && ((EntityOwnable) this.handle).getOwnerName().equals(((EntityOwnable) entityliving).getOwnerName())) {
                    return false;
                }

                if (entityliving == ((EntityOwnable) this.handle).getOwner()) {
                    return false;
                }
            } else if (entityliving instanceof EntityHuman && !flag && ((EntityHuman) entityliving).abilities.isInvulnerable) {
                return false;
            }

            if (!NMSEntityUtil.isInGuardedAreaOf(this.handle, MathHelper.floor(entityliving.locX), MathHelper.floor(entityliving.locY), MathHelper.floor(entityliving.locZ))) {
                return false;
            } else if (this.checkSenses && !NMSEntityUtil.getEntitySenses(this.handle).canSee(entityliving)) {
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
                } else if (this instanceof BehaviourOwnerHurtByTarget) {
                    reason = EntityTargetEvent.TargetReason.TARGET_ATTACKED_OWNER;
                } else if (this instanceof BehaviourOwnerHurtTarget) {
                    reason = EntityTargetEvent.TargetReason.OWNER_ATTACKED_TARGET;
                }

                org.bukkit.event.entity.EntityTargetLivingEntityEvent event = org.bukkit.craftbukkit.v1_7_R1.event.CraftEventFactory.callEntityTargetLivingEvent(this.controllableEntity.getHandle(), entityliving, reason);
                if (event.isCancelled() || event.getTarget() == null) {
                    this.controllableEntity.setTarget(null);
                    return false;
                } else if (entityliving.getBukkitEntity() != event.getTarget()) {
                    this.controllableEntity.setTarget(event.getTarget());
                }
                if (this.controllableEntity.getHandle() instanceof EntityCreature) {
                    ((EntityCreature) this.controllableEntity.getHandle()).target = ((CraftEntity) event.getTarget()).getHandle();
                }
                // CraftBukkit end

                return true;
            }
        }
    }

    private boolean attack(EntityLiving entityliving) {
        this.ticksAfterLastAttack = 10 + this.handle.aI().nextInt(5);
        PathEntity pathentity = NMSEntityUtil.getNavigation(this.handle).a(entityliving);

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
        if (this.handle instanceof EntityInsentient) {
            return ((EntityInsentient) this.handle).a(oclass);
        } else {
            return EntityCreeper.class != oclass && EntityGhast.class != oclass;
        }
    }
}