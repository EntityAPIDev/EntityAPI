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
import net.minecraft.server.v1_8_R1.Vec3D;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftLivingEntity;
import org.bukkit.util.Vector;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_8_R1.NMSEntityUtil;
import org.entityapi.nms.v1_8_R1.RandomPositionGenerator;
import org.entityapi.nms.v1_8_R1.entity.mind.behaviour.BehaviourGoalBase;

public class BehaviourGoalMoveTowardsTarget extends BehaviourGoalBase {

    private EntityLiving target;
    private double targetX;
    private double targetY;
    private double targetZ;
    private double minDistance;
    private double navigationSpeed;

    public BehaviourGoalMoveTowardsTarget(ControllableEntity controllableEntity, double minDistance, double navigationSpeed) {
        super(controllableEntity);
        this.minDistance = minDistance;
        this.navigationSpeed = navigationSpeed;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.INSTINCT;
    }

    @Override
    public String getDefaultKey() {
        return "Move Towards Target";
    }

    @Override
    public boolean shouldStart() {
        this.target = ((CraftLivingEntity) this.getControllableEntity().getTarget()).getHandle();
        if (this.target == null) {
            return false;
        } else if (this.target.e(this.getHandle()) > (double) (this.minDistance * this.minDistance)) {
            return false;
        } else {
            Vec3D vec3d = RandomPositionGenerator.a(this.getHandle(), 16, 7, this.getHandle().world.getVec3DPool().create(this.target.locX, this.target.locY, this.target.locZ));

            if (vec3d == null) {
                return false;
            } else {
                this.targetX = vec3d.c;
                this.targetY = vec3d.d;
                this.targetZ = vec3d.e;
                return true;
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        return !NMSEntityUtil.getNavigation(this.getHandle()).g() && this.target.isAlive() && this.target.e(this.getHandle()) < (double) (this.minDistance * this.minDistance);
    }

    @Override
    public void start() {
        this.getControllableEntity().navigateTo(new Vector(this.targetX, this.targetY, this.targetZ), this.navigationSpeed > 0 ? this.navigationSpeed : this.getControllableEntity().getSpeed());
    }

    @Override
    public void finish() {
        this.target = null;
    }

    @Override
    public void tick() {

    }
}