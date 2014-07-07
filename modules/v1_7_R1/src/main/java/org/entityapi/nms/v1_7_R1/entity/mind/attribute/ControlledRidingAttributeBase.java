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

package org.entityapi.nms.v1_7_R1.entity.mind.attribute;

import com.captainbern.reflection.Reflection;
import net.minecraft.server.v1_7_R1.EntityHorse;
import net.minecraft.server.v1_7_R1.EntityLiving;
import org.entityapi.api.entity.mind.Mind;
import org.entityapi.api.entity.mind.attribute.ControlledRidingAttribute;

import static com.captainbern.reflection.matcher.Matchers.withExactType;

public class ControlledRidingAttributeBase extends ControlledRidingAttribute {

    private boolean vehicleMotionOverriden;
    private boolean jumpingEnabled;
    private boolean canFly;

    public ControlledRidingAttributeBase() {
    }

    public ControlledRidingAttributeBase(boolean vehicleMotionOverriden, boolean jumpingEnabled, boolean canFly) {
        this.vehicleMotionOverriden = vehicleMotionOverriden;
        this.jumpingEnabled = jumpingEnabled;
        this.canFly = canFly;
    }

    @Override
    public void onRide(float[] motion) {
        EntityLiving entity = (EntityLiving) this.getControllableEntity().getHandle();
        if (entity.passenger == null) {
            return;
        }

        entity.X = 0.5F;

        entity.lastYaw = entity.yaw = entity.passenger.yaw;
        entity.pitch = entity.passenger.pitch * 0.5F;

        if (this.isVehicleMotionOverriden() || motion[0] == 0.0F) {
            motion[0] = ((EntityLiving) entity.passenger).be * 0.5F;
        }
        if (this.isVehicleMotionOverriden() || motion[2] == 0.0F) {
            motion[2] = ((EntityLiving) entity.passenger).bf;
        }

        // boolean bD
        boolean jumping = (Boolean) new Reflection().reflect(EntityHorse.class).getSafeFields(withExactType(Boolean.class)).get(2).getAccessor().get(entity);
        if (this.canFly()) {
            if (jumping) {
                motion[1] = 0.5F;
            } else if (entity.passenger.pitch >= 50) {
                motion[1] = -0.25F;
            }
        } else if (entity.onGround && this.isJumpingEnabled()) {
            if (jumping) {
                motion[1] = 0.5F;
            }
        }
    }

    @Override
    public boolean canFly() {
        return canFly;
    }

    @Override
    public void setCanFly(boolean flag) {
        this.canFly = flag;
    }

    @Override
    public boolean isJumpingEnabled() {
        return jumpingEnabled;
    }

    @Override
    public void setJumpingEnabled(boolean flag) {
        this.jumpingEnabled = flag;
    }

    @Override
    public boolean isVehicleMotionOverriden() {
        return vehicleMotionOverriden;
    }

    @Override
    public void setVehicleMotionOverriden(boolean flag) {
        this.vehicleMotionOverriden = flag;
    }

    @Override
    public ControlledRidingAttributeBase copyTo(Mind mind) {
        return new ControlledRidingAttributeBase(this.vehicleMotionOverriden, this.jumpingEnabled, this.canFly);
    }
}