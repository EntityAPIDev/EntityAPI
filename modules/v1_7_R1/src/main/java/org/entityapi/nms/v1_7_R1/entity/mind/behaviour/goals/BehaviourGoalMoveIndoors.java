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

import net.minecraft.server.v1_7_R1.MathHelper;
import net.minecraft.server.v1_7_R1.Vec3D;
import net.minecraft.server.v1_7_R1.Village;
import net.minecraft.server.v1_7_R1.VillageDoor;
import org.bukkit.util.Vector;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.RandomPositionGenerator;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourGoalBase;

public class BehaviourGoalMoveIndoors extends BehaviourGoalBase {

    private VillageDoor villageDoor;
    private int indoorsX = -1;
    private int indoorsZ = -1;
    private double navigationSpeed;

    public BehaviourGoalMoveIndoors(ControllableEntity controllableEntity, double navigationSpeed) {
        super(controllableEntity);
        this.navigationSpeed = navigationSpeed;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.INSTINCT;
    }

    @Override
    public String getDefaultKey() {
        return "Move Indoors";
    }

    @Override
    public boolean shouldStart() {
        int locX = MathHelper.floor(this.getHandle().locX);
        int locY = MathHelper.floor(this.getHandle().locY);
        int locZ = MathHelper.floor(this.getHandle().locZ);

        if ((!this.getHandle().world.v() || this.getHandle().world.P() || !this.getHandle().world.getBiome(locX, locZ).e()) && !this.getHandle().world.worldProvider.g) {
            if (this.getHandle().aI().nextInt(50) != 0) {
                return false;
            } else if (this.indoorsX != -1 && this.getHandle().e((double) this.indoorsX, this.getHandle().locY, (double) this.indoorsZ) < 4.0D) {
                return false;
            } else {
                Village village = this.getHandle().world.villages.getClosestVillage(locX, locY, locZ, 14);

                if (village == null) {
                    return false;
                } else {
                    this.villageDoor = village.c(locX, locY, locZ);
                    return this.villageDoor != null;
                }
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean shouldContinue() {
        return !NMSEntityUtil.getNavigation(this.getHandle()).g();
    }

    @Override
    public void start() {
        this.indoorsX = -1;
        if (this.getHandle().e((double) this.villageDoor.getIndoorsX(), (double) this.villageDoor.locY, (double) this.villageDoor.getIndoorsZ()) > 256.0D) {
            Vec3D vec3d = RandomPositionGenerator.a(this.getHandle(), 14, 3, this.getHandle().world.getVec3DPool().create((double) this.villageDoor.getIndoorsX() + 0.5D, (double) this.villageDoor.getIndoorsY(), (double) this.villageDoor.getIndoorsZ() + 0.5D));

            if (vec3d != null) {
                this.getControllableEntity().navigateTo(new Vector(vec3d.c, vec3d.d, vec3d.e), this.navigationSpeed > 0 ? this.navigationSpeed : this.getControllableEntity().getSpeed());
            }
        } else {
            this.getControllableEntity().navigateTo(new Vector((double) this.villageDoor.getIndoorsX() + 0.5D, (double) this.villageDoor.getIndoorsY(), (double) this.villageDoor.getIndoorsZ() + 0.5D), this.navigationSpeed > 0 ? this.navigationSpeed : this.getControllableEntity().getSpeed());
        }
    }

    @Override
    public void finish() {
        this.indoorsX = this.villageDoor.getIndoorsX();
        this.indoorsZ = this.villageDoor.getIndoorsZ();
        this.villageDoor = null;
    }

    @Override
    public void tick() {

    }
}