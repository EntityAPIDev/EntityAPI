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

package org.entityapi.nms.v1_7_R1.entity.mind.attribute;

import net.minecraft.server.v1_7_R1.EntityLiving;
import org.entityapi.api.mind.Attribute;
import org.entityapi.api.mind.Mind;
import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.nms.v1_7_R1.BasicEntityUtil;

import java.lang.reflect.Field;

public class RideAttribute extends Attribute {

    private boolean vehicleMotionOverriden;
    private boolean jumpingEnabled;
    private boolean canFly;

    public RideAttribute(Mind mind) {
        super(mind);
    }

    public void onRide(float[] motion) {
        EntityLiving entity = ((BasicEntityUtil) EntityAPI.getBasicEntityUtil()).getHandle(this.getControllableEntity());
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

        boolean jumping = false;
        try {
            Field field_isJumping = EntityLiving.class.getDeclaredField("bd");
            field_isJumping.setAccessible(true);
            jumping = field_isJumping.getBoolean(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public String getKey() {
        return "RIDE";
    }
}