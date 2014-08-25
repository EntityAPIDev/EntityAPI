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

package org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.EntityHorse;
import net.minecraft.server.v1_7_R1.EntityHuman;
import net.minecraft.server.v1_7_R1.EntityInsentient;
import net.minecraft.server.v1_7_R1.Vec3D;
import org.bukkit.util.Vector;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.attribute.TamedRidingAttribute;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.RandomPositionGenerator;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourGoalBase;

public class BehaviourGoalTameByRiding extends BehaviourGoalBase {

    private double walkX;
    private double walkY;
    private double walkZ;
    private double walkSpeed;

    public BehaviourGoalTameByRiding(ControllableEntity controllableEntity, double walkSpeed) {
        super(controllableEntity);
        this.walkSpeed = walkSpeed;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.INSTINCT;
    }

    @Override
    public String getDefaultKey() {
        return "TameByRiding";
    }

    private boolean isRidingAllowed() {
        if (this.getHandle() instanceof EntityHorse) {
            return ((EntityHorse) this.getHandle()).isTame();
        }
        TamedRidingAttribute tamedRidingAttribute = this.getControllableEntity().getMind().getAttribute(TamedRidingAttribute.class);
        if (tamedRidingAttribute != null) {
            return tamedRidingAttribute.isRideable();
        }
        return false;
    }

    private void increaseSuccessChance(int amount) {
        if (this.getHandle() instanceof EntityHorse) {
            ((EntityHorse) this.getHandle()).v(amount);
        } else {
            this.getControllableEntity().getMind().getAttribute(TamedRidingAttribute.class).increaseSuccessChance(amount);
        }
    }

    private int getTemper() {
        if (this.getHandle() instanceof EntityHorse) {
            return ((EntityHorse) this.getHandle()).getTemper();
        } else {
            return this.getControllableEntity().getMind().getAttribute(TamedRidingAttribute.class).getTemper();
        }
    }

    private int getMaxDomestication() {
        if (this.getHandle() instanceof EntityHorse) {
            return ((EntityHorse) this.getHandle()).getMaxDomestication();
        } else {
            return this.getControllableEntity().getMind().getAttribute(TamedRidingAttribute.class).getMaxDomestication();
        }
    }

    @Override
    public boolean shouldStart() {
        if (!(this.getHandle() instanceof EntityHorse) && !this.getControllableEntity().getMind().hasAttribute(TamedRidingAttribute.class)) {
            return false;
        }
        if (!this.isRidingAllowed() && this.getHandle().passenger != null) {
            Vec3D vec3d = RandomPositionGenerator.a(this.getHandle(), 5, 4);

            if (vec3d == null) {
                return false;
            } else {
                this.walkX = vec3d.c;
                this.walkY = vec3d.d;
                this.walkZ = vec3d.e;
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean shouldContinue() {
        return !NMSEntityUtil.getNavigation(this.getHandle()).g() && this.getHandle().passenger != null;
    }

    @Override
    public void start() {
        this.getControllableEntity().navigateTo(new Vector(this.walkX, this.walkY, this.walkZ), this.walkSpeed > 0 ? this.walkSpeed : this.getControllableEntity().getSpeed());
    }

    @Override
    public void tick() {
        if (this.getHandle().aI().nextInt(50) == 0) {
            if (this.getHandle().passenger instanceof EntityHuman) {
                int temper = this.getTemper();
                int maxDomestication = this.getMaxDomestication();

                boolean eventCancelled = false;

                if (this.getHandle() instanceof EntityInsentient) {
                    eventCancelled = org.bukkit.craftbukkit.v1_7_R1.event.CraftEventFactory.callEntityTameEvent((EntityInsentient) this.getHandle(), (EntityHuman) this.getHandle().passenger).isCancelled();
                }

                // CraftBukkit
                if (maxDomestication > 0 && this.getHandle().aI().nextInt(maxDomestication) < temper && !eventCancelled && this.getHandle().passenger instanceof EntityHuman) {
                    this.getHandle().h((EntityHuman) this.getHandle().passenger);
                    this.getHandle().world.broadcastEntityEffect(this.getHandle(), (byte) 7);
                    return;
                }

                this.increaseSuccessChance(5);
            }

            // CraftBukkit start - Handle dismounting to account for VehicleExitEvent being fired.
            if (this.getHandle().passenger != null) {
                this.getHandle().passenger.mount(null);
                // If the entity still has a passenger, then a plugin cancelled the event.
                if (this.getHandle().passenger != null) {
                    return;
                }
            }
            // this.getHandle().passenger = null;
            // CraftBukkit end
            if (this.getHandle() instanceof EntityHorse) {
                ((EntityHorse) this.getHandle()).cH();
            }
            this.getHandle().world.broadcastEntityEffect(this.getHandle(), (byte) 6);
        }
    }
}