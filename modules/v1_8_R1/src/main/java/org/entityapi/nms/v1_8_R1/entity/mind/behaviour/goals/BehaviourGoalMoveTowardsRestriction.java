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

import net.minecraft.server.v1_8_R1.ChunkCoordinates;
import net.minecraft.server.v1_8_R1.Vec3D;
import org.bukkit.util.Vector;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_8_R1.NMSEntityUtil;
import org.entityapi.nms.v1_8_R1.RandomPositionGenerator;
import org.entityapi.nms.v1_8_R1.entity.mind.behaviour.BehaviourGoalBase;

public class BehaviourGoalMoveTowardsRestriction extends BehaviourGoalBase {

    private double targetX;
    private double targetY;
    private double targetZ;
    private double navigationSpeed;

    public BehaviourGoalMoveTowardsRestriction(ControllableEntity controllableEntity, double navigationSpeed) {
        super(controllableEntity);
        this.navigationSpeed = navigationSpeed;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.INSTINCT;
    }

    @Override
    public String getDefaultKey() {
        return "Move Towards Restriction";
    }

    @Override
    public boolean shouldStart() {
        if (NMSEntityUtil.isInHomeArea(this.getHandle())) {
            return false;
        } else {
            ChunkCoordinates chunkcoordinates = NMSEntityUtil.getChunkCoordinates(this.getHandle());
            Vec3D vec3d = RandomPositionGenerator.a(this.getHandle(), 16, 7, this.getHandle().world.getVec3DPool().create((double) chunkcoordinates.x, (double) chunkcoordinates.y, (double) chunkcoordinates.z));

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
        return !NMSEntityUtil.getNavigation(this.getHandle()).g();
    }

    @Override
    public void start() {
        this.getControllableEntity().navigateTo(new Vector(this.targetX, this.targetY, this.targetZ), this.navigationSpeed > 0 ? this.navigationSpeed : this.getControllableEntity().getSpeed());
    }

    @Override
    public void tick() {

    }
}