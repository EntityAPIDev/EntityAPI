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

import org.entityapi.api.entity.ControllableEntity;

public class BehaviourGoalOpenDoor extends BehaviourGoalDoorInteract {

    private boolean closeDoor;
    private int closeTicks;

    public BehaviourGoalOpenDoor(ControllableEntity controllableEntity, boolean searchForIronDoor, boolean closeDoor) {
        super(controllableEntity, searchForIronDoor);
        this.closeDoor = closeDoor;
    }

    @Override
    public String getDefaultKey() {
        return "Open Door";
    }

    @Override
    public boolean shouldContinue() {
        return this.closeDoor && this.closeTicks > 0 && super.shouldContinue();
    }

    @Override
    public void start() {
        this.closeTicks = 20;
        this.door.setDoor(this.getHandle().world, this.pointX, this.pointY, this.pointZ, true);
    }

    @Override
    public void finish() {
        if (this.closeDoor) {
            this.door.setDoor(this.getHandle().world, this.pointX, this.pointY, this.pointZ, false);
        }
    }

    @Override
    public void tick() {
        --this.closeTicks;
        super.tick();
    }
}