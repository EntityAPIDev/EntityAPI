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

import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntityLiving;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftLivingEntity;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.api.utils.EntityUtil;
import org.entityapi.nms.v1_8_R1.NMSEntityUtil;
import org.entityapi.nms.v1_8_R1.entity.mind.behaviour.BehaviourGoalBase;

public class BehaviourGoalLookAtNearestEntity extends BehaviourGoalBase {

    protected Entity target;
    private float minDist;
    private int ticks;
    private float chance;
    private Class<? extends Entity> entityClass;

    public BehaviourGoalLookAtNearestEntity(ControllableEntity controllableEntity, Class<? extends org.bukkit.entity.Entity> classType, float minDistance, float chance) {
        super(controllableEntity);
        this.entityClass = (Class<? extends Entity>) EntityUtil.getNmsClassFor(classType);
        if (this.entityClass == null || !(EntityLiving.class.isAssignableFrom(entityClass))) {
            throw new IllegalArgumentException("Could not find valid NMS class for " + entityClass.getSimpleName());
        }
        this.minDist = minDistance;
        this.chance = chance;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ATTENTION;
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