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

import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.MathHelper;
import net.minecraft.server.v1_7_R1.World;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftEntity;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;

public class BehaviourFollowExact extends BehaviourBase {

    private Entity toFollow;
    private int ticks;
    private float minDist;
    private float maxDist;
    private boolean avoidWater;

    public BehaviourFollowExact(ControllableEntity controllableEntity, org.bukkit.entity.Entity toFollow, float minDist) {
        this(controllableEntity, toFollow, minDist, 144.0F);
    }

    public BehaviourFollowExact(ControllableEntity controllableEntity, org.bukkit.entity.Entity toFollow, float minDistance, float maxDistance) {
        super(controllableEntity);
        this.toFollow = ((CraftEntity) toFollow).getHandle();
        this.minDist = minDistance;
        this.maxDist = maxDistance;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ACTION;
    }

    @Override
    public String getDefaultKey() {
        return "Follow Exact";
    }

    @Override
    public boolean shouldStart() {
        if (toFollow == null) {
            return false;
        } else if (!this.toFollow.isAlive()) {
            return false;
        } else if (this.toFollow == this.getHandle()) {
            return false;
        } else if (this.getHandle().e(this.toFollow) < (this.minDist * this.minDist)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean shouldContinue() {
        return !NMSEntityUtil.getNavigation(this.getHandle()).g() && this.getHandle().e(this.toFollow) > (double) (this.minDist * this.minDist);
    }

    @Override
    public void start() {
        this.ticks = 0;
        this.avoidWater = NMSEntityUtil.getNavigation(this.getHandle()).a();
        NMSEntityUtil.getNavigation(this.getHandle()).a(false);
    }

    @Override
    public void finish() {
        NMSEntityUtil.getNavigation(this.getHandle()).h();
        NMSEntityUtil.getNavigation(this.getHandle()).a(this.avoidWater);
    }

    @Override
    public void tick() {
        NMSEntityUtil.getControllerLook(this.getHandle()).a(this.toFollow, 10.0F, (float) NMSEntityUtil.getMaxHeadRotation(this.getHandle()));
        if (--this.ticks <= 0) {
            this.ticks = 10;
            if (!NMSEntityUtil.getNavigation(this.getHandle()).a(this.toFollow, this.getControllableEntity().getSpeed())) {
                if (this.getHandle().e(this.toFollow) >= (this.maxDist * this.maxDist)) {
                    int x = MathHelper.floor(this.toFollow.locX) - 2;
                    int y = MathHelper.floor(this.toFollow.locZ) - 2;
                    int z = MathHelper.floor(this.toFollow.boundingBox.b);

                    for (int l = 0; l <= 4; ++l) {
                        for (int i1 = 0; i1 <= 4; ++i1) {
                            if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && World.a(this.getHandle().world, x + l, z - 1, y + i1) && !this.getHandle().world.getType(x + l, z, y + i1).r() && !this.getHandle().world.getType(x + l, z + 1, y + i1).r()) {
                                this.getHandle().setPositionRotation((double) ((float) (x + l) + 0.5F), (double) z, (double) ((float) (y + i1) + 0.5F), this.getHandle().yaw, this.getHandle().pitch);
                                NMSEntityUtil.getNavigation(this.getHandle()).h();
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}