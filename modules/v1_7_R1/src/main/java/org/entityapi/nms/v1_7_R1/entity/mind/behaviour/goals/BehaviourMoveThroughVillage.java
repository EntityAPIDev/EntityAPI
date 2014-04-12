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
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.*;
import org.entityapi.nms.v1_7_R1.entity.ControllableBaseEntity;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BehaviourMoveThroughVillage extends BehaviourBase {

    private PathEntity path;
    private VillageDoor villageDoor;
    private boolean onlyMoveAtNight;
    private List<VillageDoor> doors = new ArrayList<>();

    public BehaviourMoveThroughVillage(ControllableEntity controllableEntity, boolean onlyMoveAtNight) {
        super(controllableEntity);
        this.onlyMoveAtNight = onlyMoveAtNight;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.INSTINCT;
    }

    @Override
    public String getDefaultKey() {
        return "Move Through Village";
    }

    @Override
    public boolean shouldStart() {
        this.cleanup();
        if (this.onlyMoveAtNight && this.getHandle().world.v()) {
            return false;
        } else {
            Village village = this.getHandle().world.villages.getClosestVillage(MathHelper.floor(this.getHandle().locX), MathHelper.floor(this.getHandle().locY), MathHelper.floor(this.getHandle().locZ), 0);

            if (village == null) {
                return false;
            } else {
                this.villageDoor = this.getNearestDoor(village);
                if (this.villageDoor == null) {
                    return false;
                } else {
                    boolean flag = NMSEntityUtil.getNavigation(this.getHandle()).c();

                    NMSEntityUtil.getNavigation(this.getHandle()).b(false);
                    this.path = NMSEntityUtil.getNavigation(this.getHandle()).a((double) this.villageDoor.locX, (double) this.villageDoor.locY, (double) this.villageDoor.locZ);
                    NMSEntityUtil.getNavigation(this.getHandle()).b(flag);
                    if (this.path != null) {
                        return true;
                    } else {
                        Vec3D vec3d = org.entityapi.nms.v1_7_R1.RandomPositionGenerator.a(this.getHandle(), 10, 7, this.getHandle().world.getVec3DPool().create((double) this.villageDoor.locX, (double) this.villageDoor.locY, (double) this.villageDoor.locZ));

                        if (vec3d == null) {
                            return false;
                        } else {
                            NMSEntityUtil.getNavigation(this.getHandle()).b(false);
                            this.path = NMSEntityUtil.getNavigation(this.getHandle()).a(vec3d.c, vec3d.d, vec3d.e);
                            NMSEntityUtil.getNavigation(this.getHandle()).b(flag);
                            return this.path != null;
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        if (NMSEntityUtil.getNavigation(this.getHandle()).g()) {
            return false;
        } else {
            float f = this.getHandle().width + 4.0F;
            return this.getHandle().e((double) this.villageDoor.locX, (double) this.villageDoor.locY, (double) this.villageDoor.locZ) > (double) (f * f);
        }
    }

    @Override
    public void start() {
        ((ControllableBaseEntity) this.getControllableEntity()).navigateTo(this.path);
    }

    @Override
    public void finish() {
        if (NMSEntityUtil.getNavigation(this.getHandle()).g() || this.getHandle().e((double) this.villageDoor.locX, (double) this.villageDoor.locY, (double) this.villageDoor.locZ) < 16.0D) {
            this.doors.add(this.villageDoor);
        }
    }

    @Override
    public void tick() {

    }

    private VillageDoor getNearestDoor(Village village) {
        VillageDoor door = null;
        int dist = Integer.MAX_VALUE;
        List doorsInVillage = village.getDoors();
        Iterator iterator = doorsInVillage.iterator();

        while (iterator.hasNext()) {
            VillageDoor doorToCheck = (VillageDoor) iterator.next();
            int distToDoor = doorToCheck.b(MathHelper.floor(this.getHandle().locX), MathHelper.floor(this.getHandle().locY), MathHelper.floor(this.getHandle().locZ));

            if (distToDoor < dist && !this.isDoorIntList(doorToCheck)) {
                door = doorToCheck;
                dist = distToDoor;
            }
        }

        return door;
    }

    private boolean isDoorIntList(VillageDoor door) {
        Iterator doors = this.doors.iterator();

        VillageDoor villageDoor;

        do {
            if (!doors.hasNext()) {
                return false;
            }

            villageDoor = (VillageDoor) doors.next();
        } while (door.locX != villageDoor.locX || door.locY != villageDoor.locY || door.locZ != villageDoor.locZ);

        return true;
    }

    private void cleanup() {
        if (this.doors.size() > 15) {
            this.doors.remove(0);
        }
    }
}