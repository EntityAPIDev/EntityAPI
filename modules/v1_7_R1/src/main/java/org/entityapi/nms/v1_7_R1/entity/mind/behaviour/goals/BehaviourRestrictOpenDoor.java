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

import net.minecraft.server.v1_7_R1.MathHelper;
import net.minecraft.server.v1_7_R1.Village;
import net.minecraft.server.v1_7_R1.VillageDoor;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;

public class BehaviourRestrictOpenDoor extends BehaviourBase {

    private VillageDoor door;

    public BehaviourRestrictOpenDoor(ControllableEntity controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.SUBCONSCIOUS;
    }

    @Override
    public String getDefaultKey() {
        return "Restrict Open Door";
    }

    @Override
    public boolean shouldStart() {
        if (this.getHandle().world.v()) {
            return false;
        } else {
            Village closestVillage = this.getHandle().world.villages.getClosestVillage(MathHelper.floor(this.getHandle().locX), MathHelper.floor(this.getHandle().locY), MathHelper.floor(this.getHandle().locZ), 16);

            if (closestVillage == null) {
                return false;
            } else {
                this.door = closestVillage.b(MathHelper.floor(this.getHandle().locX), MathHelper.floor(this.getHandle().locY), MathHelper.floor(this.getHandle().locZ));
                return this.door == null ? false : (double) this.door.c(MathHelper.floor(this.getHandle().locX), MathHelper.floor(this.getHandle().locY), MathHelper.floor(this.getHandle().locZ)) < 2.25D;
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        return this.getHandle().world.v() ? false : !this.door.removed && this.door.a(MathHelper.floor(this.getHandle().locX), MathHelper.floor(this.getHandle().locZ));
    }

    @Override
    public void start() {
        NMSEntityUtil.getNavigation(this.getHandle()).b(false);
        NMSEntityUtil.getNavigation(this.getHandle()).c(false);
    }

    @Override
    public void finish() {
        NMSEntityUtil.getNavigation(this.getHandle()).b(true);
        NMSEntityUtil.getNavigation(this.getHandle()).c(true);
        this.door = null;
    }

    @Override
    public void tick() {
        this.door.e();
    }
}