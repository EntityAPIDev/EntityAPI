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

package org.entityapi.api.mind.attribute;

import org.entityapi.api.mind.Mind;

public abstract class RideControlAttribute extends Attribute {

    protected RideControlAttribute(Mind mind) {
        super(mind);
    }

    @Override
    public String getKey() {
        return "Ride";
    }

    public abstract void onRide(float[] motion);

    public abstract boolean canFly();

    public abstract void setCanFly(boolean flag);

    public abstract boolean isJumpingEnabled();

    public abstract void setJumpingEnabled(boolean flag);

    public abstract boolean isVehicleMotionOverriden();

    public abstract void setVehicleMotionOverriden(boolean flag);
}