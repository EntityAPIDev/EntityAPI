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

import net.minecraft.server.v1_7_R1.*;
import org.bukkit.entity.Ocelot;
import org.bukkit.util.Vector;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.api.entity.type.ControllableOcelot;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourGoalBase;

public class BehaviourGoalSitOnBlock<T extends ControllableOcelot> extends BehaviourGoalBase<T, EntityOcelot> {

    private int sitTicks;
    private int actionTicks;
    private int maxSitTicks;
    private int targetX;
    private int targetY;
    private int targetZ;
    private double navigationSpeed;

    public BehaviourGoalSitOnBlock(T controllableEntity, double navigationSpeed) {
        super(controllableEntity);
        this.navigationSpeed = navigationSpeed;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.IMPULSE;
    }

    @Override
    public String getDefaultKey() {
        return "Sit On Block";
    }

    @Override
    public boolean shouldStart() {
        return this.getHandle().isTamed() && !this.getHandle().isSitting() && this.getHandle().aI().nextDouble() <= 0.006500000134110451D && this.isSuitableBlockNearby();
    }

    @Override
    public boolean shouldContinue() {
        return this.sitTicks <= this.maxSitTicks && this.actionTicks <= 60 && this.isSuitableBlock(this.getHandle().world, this.targetX, this.targetY, this.targetZ);
    }

    @Override
    public void start() {
        this.getControllableEntity().navigateTo(new Vector((double) ((float) this.targetX) + 0.5D, (double) (this.targetY + 1), (double) ((float) this.targetZ) + 0.5D), this.navigationSpeed > 0 ? this.navigationSpeed : this.getControllableEntity().getSpeed());
        this.sitTicks = 0;
        this.actionTicks = 0;
        this.maxSitTicks = this.getHandle().aI().nextInt(this.getHandle().aI().nextInt(1200) + 1200) + 1200;
        this.getHandle().getGoalSit().setSitting(false);
    }

    @Override
    public void finish() {
        this.getHandle().setSitting(false);
    }

    @Override
    public void tick() {
        ++this.sitTicks;
        this.getHandle().getGoalSit().setSitting(false);
        if (this.getHandle().e((double) this.targetX, (double) (this.targetY + 1), (double) this.targetZ) > 1.0D) {
            this.getHandle().setSitting(false);
            this.getControllableEntity().navigateTo(new Vector((double) ((float) this.targetX) + 0.5D, (double) (this.targetY + 1), (double) ((float) this.targetZ) + 0.5D), this.navigationSpeed > 0 ? this.navigationSpeed : this.getControllableEntity().getSpeed());
            ++this.actionTicks;
        } else if (!this.getHandle().isSitting()) {
            this.getHandle().setSitting(true);
        } else {
            --this.actionTicks;
        }
    }

    private boolean isSuitableBlockNearby() {
        int y = (int) this.getHandle().locY;
        double minDist = 2.147483647E9D;

        for (int x = (int) this.getHandle().locX - 8; (double) x < this.getHandle().locX + 8.0D; ++x) {
            for (int z = (int) this.getHandle().locZ - 8; (double) z < this.getHandle().locZ + 8.0D; ++z) {
                if (this.isSuitableBlock(this.getHandle().world, x, y, z) && this.getHandle().world.isEmpty(x, y + 1, z)) {
                    double distance = this.getHandle().e((double) x, (double) y, (double) z);

                    if (distance < minDist) {
                        this.targetX = x;
                        this.targetY = y;
                        this.targetZ = z;
                        minDist = distance;
                    }
                }
            }
        }

        return minDist < 2.147483647E9D;
    }

    private boolean isSuitableBlock(World world, int i, int j, int k) {
        Block block = world.getType(i, j, k);
        int blockData = world.getData(i, j, k);

        if (block == Blocks.CHEST) {
            TileEntityChest tileentitychest = (TileEntityChest) world.getTileEntity(i, j, k);

            if (tileentitychest.o < 1) {
                return true;
            }
        } else {
            if (block == Blocks.BURNING_FURNACE) {
                return true;
            }

            if (block == Blocks.BED && !BlockBed.b(blockData)) {
                return true;
            }
        }

        return false;
    }
}