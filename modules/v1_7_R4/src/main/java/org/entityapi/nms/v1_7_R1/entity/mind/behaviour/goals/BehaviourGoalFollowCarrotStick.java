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

package org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals;

import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourGoalBase;

public class BehaviourGoalFollowCarrotStick extends BehaviourGoalBase {

    private float maxSpeed;
    private float currentSpeed;
    private boolean boostSpeed;
    private int navigationSpeedBoostTicks;
    private int maxSpeedBoostTicks;

    public BehaviourGoalFollowCarrotStick(ControllableEntity controllableEntity, float maxSpeed) {
        super(controllableEntity);
        this.maxSpeed = maxSpeed;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.FOOD;
    }

    @Override
    public String getDefaultKey() {
        return "Follow Carrot";
    }

    @Override
    public boolean shouldStart() {
        return this.getHandle().isAlive() && this.getHandle().passenger != null && this.getHandle().passenger instanceof EntityHuman && (this.boostSpeed || NMSEntityUtil.canBeSteered(this.getHandle()));
    }

    @Override
    public void start() {
        this.currentSpeed = 0.0F;
    }

    @Override
    public void finish() {
        this.boostSpeed = false;
        this.currentSpeed = 0.0F;
    }

    @Override
    public void tick() {
        EntityHuman passenger = (EntityHuman) this.getHandle().passenger;
        float f = MathHelper.g(passenger.yaw - this.getHandle().yaw) * 0.5F;

        if (f > 5.0F) {
            f = 5.0F;
        }

        if (f < -5.0F) {
            f = -5.0F;
        }

        this.getHandle().yaw = MathHelper.g(this.getHandle().yaw + f);
        if (this.currentSpeed < this.maxSpeed) {
            this.currentSpeed += (this.maxSpeed - this.currentSpeed) * 0.01F;
        }

        if (this.currentSpeed > this.maxSpeed) {
            this.currentSpeed = this.maxSpeed;
        }

        int locX = MathHelper.floor(this.getHandle().locX);
        int locY = MathHelper.floor(this.getHandle().locY);
        int locZ = MathHelper.floor(this.getHandle().locZ);
        float navigationSpeed = this.currentSpeed;

        if (this.boostSpeed) {
            if (this.navigationSpeedBoostTicks++ > this.maxSpeedBoostTicks) {
                this.boostSpeed = false;
            }

            navigationSpeed += navigationSpeed * 1.15F * MathHelper.sin((float) this.navigationSpeedBoostTicks / (float) this.maxSpeedBoostTicks * 3.1415927F);
        }

        float blockFriction = 0.91F;

        if (this.getHandle().onGround) {
            blockFriction = this.getHandle().world.getType(MathHelper.d((float) locX), MathHelper.d((float) locY) - 1, MathHelper.d((float) locZ)).frictionFactor * 0.91F;
        }

        // MATH ;D

        float f3 = 0.16277136F / (blockFriction * blockFriction * blockFriction);
        float f4 = MathHelper.sin(this.getHandle().yaw * 3.1415927F / 180.0F);
        float f5 = MathHelper.cos(this.getHandle().yaw * 3.1415927F / 180.0F);
        float f6 = this.getHandle().bl() * f3;
        float f7 = Math.max(navigationSpeed, 1.0F);

        f7 = f6 / f7;
        float f8 = navigationSpeed * f7;
        float f9 = -(f8 * f4);
        float f10 = f8 * f5;

        if (MathHelper.abs(f9) > MathHelper.abs(f10)) {
            if (f9 < 0.0F) {
                f9 -= this.getHandle().width / 2.0F;
            }

            if (f9 > 0.0F) {
                f9 += this.getHandle().width / 2.0F;
            }

            f10 = 0.0F;
        } else {
            f9 = 0.0F;
            if (f10 < 0.0F) {
                f10 -= this.getHandle().width / 2.0F;
            }

            if (f10 > 0.0F) {
                f10 += this.getHandle().width / 2.0F;
            }
        }

        int nextX = MathHelper.floor(this.getHandle().locX + (double) f9);
        int nextZ = MathHelper.floor(this.getHandle().locZ + (double) f10);
        PathPoint path = new PathPoint(MathHelper.d(this.getHandle().width + 1.0F), MathHelper.d(this.getHandle().length + passenger.length + 1.0F), MathHelper.d(this.getHandle().width + 1.0F));

        if (locX != nextX || locZ != nextZ) {
            Block block = this.getHandle().world.getType(locX, locY, locZ);
            boolean isStep = !this.isTypeStep(block) && (block.getMaterial() != Material.AIR || !this.isTypeStep(this.getHandle().world.getType(locX, locY - 1, locZ)));

            if (isStep && Pathfinder.a(this.getHandle(), nextX, locY, nextZ, path, false, false, true) == 0 && Pathfinder.a(this.getHandle(), locX, locY + 1, locZ, path, false, false, true) == 1 && Pathfinder.a(this.getHandle(), nextX, locY + 1, nextZ, path, false, false, true) == 1) {
                NMSEntityUtil.getControllerJump(this.getHandle()).a();
            }
        }

        if (!passenger.abilities.canInstantlyBuild && this.currentSpeed >= this.maxSpeed * 0.5F && this.getHandle().aI().nextFloat() < 0.006F && !this.boostSpeed) {
            ItemStack heldItem = passenger.be();

            if (heldItem != null && heldItem.getItem() == Items.CARROT_STICK) {
                heldItem.damage(1, passenger);
                if (heldItem.count == 0) {
                    ItemStack fishingRod = new ItemStack(Items.FISHING_ROD);

                    fishingRod.setTag(heldItem.tag);
                    passenger.inventory.items[passenger.inventory.itemInHandIndex] = fishingRod;
                }
            }
        }

        this.getHandle().e(0.0F, navigationSpeed);
    }

    private boolean isTypeStep(Block block) {
        return block.b() == 10 || block instanceof BlockStepAbstract;
    }

    public boolean isSpeedBoosted() {
        return this.boostSpeed;
    }

    public void boostSpeed() {
        this.boostSpeed = true;
        this.navigationSpeedBoostTicks = 0;
        this.maxSpeedBoostTicks = this.getHandle().aI().nextInt(841) + 140;
    }

    public boolean isBeingSteered() {
        return !this.isSpeedBoosted() && this.currentSpeed > this.maxSpeed * 0.3F;
    }
}