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
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;

public class BehaviourDoorInteract extends BehaviourBase {

    private boolean searchForIronDoor;
    protected int pointX;
    protected int pointY;
    protected int pointZ;
    protected BlockDoor door;
    private boolean doorFound;
    private float distX;
    private float distY;

    public BehaviourDoorInteract(ControllableEntity controllableEntity) {
        this(controllableEntity, false);
    }

    public BehaviourDoorInteract(ControllableEntity controllableEntity, boolean searchForIronDoor) {
        super(controllableEntity);
        this.searchForIronDoor = searchForIronDoor;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.SUBCONSCIOUS;
    }

    @Override
    public String getDefaultKey() {
        return "Door Interact";
    }

    @Override
    public boolean shouldStart() {
        if (!this.getHandle().positionChanged) {
            return false;
        } else {
            Navigation nav = NMSEntityUtil.getNavigation(this.getHandle());
            PathEntity path = nav.e();

            if (path != null && !path.b() && nav.c()) {
                for (int i = 0; i < Math.min(path.e() + 2, path.d()); ++i) {
                    PathPoint pathpoint = path.a(i);

                    this.pointX = pathpoint.a;
                    this.pointY = pathpoint.b + 1;
                    this.pointZ = pathpoint.c;
                    if (this.getHandle().e((double) this.pointX, this.getHandle().locY, (double) this.pointZ) <= 2.25D) {
                        this.door = this.getDoorAt(this.pointX, this.pointY, this.pointZ);
                        if (this.door != null) {
                            return true;
                        }
                    }
                }

                this.pointX = MathHelper.floor(this.getHandle().locX);
                this.pointY = MathHelper.floor(this.getHandle().locY + 1.0D);
                this.pointZ = MathHelper.floor(this.getHandle().locZ);
                this.door = this.getDoorAt(this.pointX, this.pointY, this.pointZ);
                return this.door != null;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        return !this.doorFound;
    }

    @Override
    public void start() {
        this.doorFound = false;
        this.distX = (float) ((double) ((float) this.pointX + 0.5F) - this.getHandle().locX);
        this.distY = (float) ((double) ((float) this.pointZ + 0.5F) - this.getHandle().locZ);
    }

    @Override
    public void tick() {
        float distX = (float) ((double) ((float) this.pointX + 0.5F) - this.getHandle().locX);
        float distY = (float) ((double) ((float) this.pointZ + 0.5F) - this.getHandle().locZ);
        float dist = this.distX * distX + this.distY * distY;

        if (dist < 0.0F) {
            this.doorFound = true;
        }
    }

    private BlockDoor getDoorAt(int i, int j, int k) {
        Block block = this.getHandle().world.getType(i, j, k);
        return (!this.searchForIronDoor && block == Blocks.WOODEN_DOOR) || (this.searchForIronDoor && block == Blocks.IRON_DOOR_BLOCK) ? (BlockDoor) block : null;
    }
}