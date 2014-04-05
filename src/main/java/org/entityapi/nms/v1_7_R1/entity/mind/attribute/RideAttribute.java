package org.entityapi.nms.v1_7_R1.entity.mind.attribute;

import net.minecraft.server.v1_7_R1.EntityLiving;
import org.entityapi.EntityAPICore;
import org.entityapi.api.mind.Mind;
import org.entityapi.api.mind.Attribute;
import org.entityapi.nms.v1_7_R1.BasicEntityUtil;

import java.lang.reflect.Field;

public class RideAttribute extends Attribute {

    private boolean vehicleMotionOverriden;
    private boolean jumpingEnabled;

    public RideAttribute(Mind mind) {
        super(mind);
    }

    public void onRide(float[] motion) {
        EntityLiving entity = ((BasicEntityUtil) EntityAPICore.getBasicEntityUtil()).getHandle(this.getControllableEntity());
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
        if (this.getControllableEntity().canFly()) {
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

    public boolean isJumpingEnabled() {
        return jumpingEnabled;
    }

    public void setJumpingEnabled(boolean jumpingEnabled) {
        this.jumpingEnabled = jumpingEnabled;
    }

    public boolean isVehicleMotionOverriden() {
        return vehicleMotionOverriden;
    }

    public void setVehicleMotionOverriden(boolean vehicleMotionOverriden) {
        this.vehicleMotionOverriden = vehicleMotionOverriden;
    }

    @Override
    public String getKey() {
        return "RIDE";
    }
}