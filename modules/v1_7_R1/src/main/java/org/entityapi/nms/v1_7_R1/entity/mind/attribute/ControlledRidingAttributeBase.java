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

import net.minecraft.server.v1_7_R1.EntityLiving;
import org.entityapi.api.entity.mind.Attribute;
import org.entityapi.api.entity.mind.Mind;
import org.entityapi.api.entity.mind.attribute.ControlledRidingAttribute;
import org.entityapi.internal.Constants;
import org.entityapi.reflection.SafeField;
import org.entityapi.nms.v1_7_R1.BasicEntityUtil;

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

    public void onRide(float[] motion) {
        EntityLiving entity = BasicEntityUtil.getInstance().getHandle(this.getControllableEntity());
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

        boolean jumping = new SafeField<Boolean>(EntityLiving.class, Constants.Entity.JUMP.get()).get(entity);
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

    public boolean canFly() {
        return canFly;
    }

    public void setCanFly(boolean flag) {
        this.canFly = flag;
    }

    public boolean isJumpingEnabled() {
        return jumpingEnabled;
    }

    public void setJumpingEnabled(boolean flag) {
        this.jumpingEnabled = flag;
    }

    public boolean isVehicleMotionOverriden() {
        return vehicleMotionOverriden;
    }

    public void setVehicleMotionOverriden(boolean flag) {
        this.vehicleMotionOverriden = flag;
    }

    @Override
    public ControlledRidingAttributeBase copyTo(Mind mind) {
        return new ControlledRidingAttributeBase(this.vehicleMotionOverriden, this.jumpingEnabled, this.canFly);
    }
}