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

import org.bukkit.Location;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.OneTimeBehaviourBase;

public class BehaviourMoveTowardsLocation extends OneTimeBehaviourBase {

    private Location targetLocation;
    private double stopDistance;

    public BehaviourMoveTowardsLocation(ControllableEntity controllableEntity, Location targetLocation) {
        this(controllableEntity, targetLocation, 2);
    }

    public BehaviourMoveTowardsLocation(ControllableEntity controllableEntity, Location targetLocation, double stopDistance) {
        super(controllableEntity);
        this.targetLocation = targetLocation;
        this.stopDistance = stopDistance;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.INSTINCT;
    }

    @Override
    public String getDefaultKey() {
        return "Move Towards Location";
    }

    @Override
    public boolean isFinished() {
        return !this.shouldContinue() && this.getControllableEntity().getBukkitEntity().getLocation().distanceSquared(this.targetLocation) < this.stopDistance;
    }

    @Override
    public boolean shouldStart() {
        return this.getControllableEntity().getBukkitEntity().getLocation().distanceSquared(this.targetLocation) > 1;
    }

    @Override
    public boolean shouldContinue() {
        return NMSEntityUtil.getNavigation(this.getHandle()).g();
    }

    @Override
    public void start() {
        this.getControllableEntity().navigateTo(this.targetLocation.toVector());
    }

    @Override
    public void tick() {

    }
}