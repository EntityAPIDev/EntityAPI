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

import net.minecraft.server.v1_7_R1.EntityCreature;
import net.minecraft.server.v1_7_R1.MathHelper;
import net.minecraft.server.v1_7_R1.Vec3D;
import org.bukkit.util.Vector;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourGoalBase;

import java.util.Random;

public class BehaviourGoalFleeSun extends BehaviourGoalBase {

    private double fleeX;
    private double fleeY;
    private double fleeZ;
    private double navigationSpeed;

    public BehaviourGoalFleeSun(ControllableEntity controllableEntity, double navigationSpeed) {
        super(controllableEntity);
        this.navigationSpeed = navigationSpeed;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.INSTINCT;
    }

    @Override
    public String getDefaultKey() {
        return "Flee Sun";
    }

    @Override
    public boolean shouldStart() {
        if (!this.getHandle().world.v()) {
            return false;
        } else if (!this.getHandle().isBurning()) {
            return false;
        } else if (!this.getHandle().world.i(MathHelper.floor(this.getHandle().locX), (int) this.getHandle().boundingBox.b, MathHelper.floor(this.getHandle().locZ))) {
            return false;
        } else {
            Vec3D vec3d = this.findShadySpot();

            if (vec3d == null) {
                return false;
            } else {
                this.fleeX = vec3d.c;
                this.fleeY = vec3d.d;
                this.fleeZ = vec3d.e;
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
        this.getControllableEntity().navigateTo(new Vector(this.fleeX, this.fleeY, this.fleeZ), this.navigationSpeed > 0 ? this.navigationSpeed : this.getControllableEntity().getSpeed());
    }

    @Override
    public void tick() {

    }

    private Vec3D findShadySpot() {
        Random random = this.getHandle().aI();

        for (int i = 0; i < 10; ++i) {
            int x = MathHelper.floor(this.getHandle().locX + (double) random.nextInt(20) - 10.0D);
            int y = MathHelper.floor(this.getHandle().boundingBox.b + (double) random.nextInt(6) - 3.0D);
            int z = MathHelper.floor(this.getHandle().locZ + (double) random.nextInt(20) - 10.0D);

            if (this.getHandle() instanceof EntityCreature) {
                if (!this.getHandle().world.i(x, y, z) && ((EntityCreature) this.getHandle()).a(x, y, z) < 0.0F) {
                    return this.getHandle().world.getVec3DPool().create((double) x, (double) y, (double) z);
                }
            } else {
                if (!this.getHandle().world.i(x, y, z) && (0.5F - this.getHandle().world.n(x, y, z)) < 0.0F) {
                    return this.getHandle().world.getVec3DPool().create((double) x, (double) y, (double) z);
                }
            }
        }

        return null;
    }
}