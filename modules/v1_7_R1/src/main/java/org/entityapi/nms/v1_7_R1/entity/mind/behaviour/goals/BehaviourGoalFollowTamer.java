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

import net.minecraft.server.v1_7_R1.*;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.attribute.TamingAttribute;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourGoalBase;

public class BehaviourGoalFollowTamer extends BehaviourGoalBase {

    private EntityLiving tamer;
    private int followTicks;
    private float maxDistance;
    private float minDistance;
    private boolean waterAvoidance;
    private double navigationSpeed;

    public BehaviourGoalFollowTamer(ControllableEntity controllableEntity, float maxDistance, float minDistance, double navigationSpeed) {
        super(controllableEntity);
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;
        this.navigationSpeed = navigationSpeed;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ACTION;
    }

    @Override
    public String getDefaultKey() {
        return "Follow Tamer";
    }

    private boolean isTamed() {
        if (this.getHandle() instanceof EntityTameableAnimal) {
            return ((EntityTameableAnimal) this.getHandle()).isTamed();
        }
        TamingAttribute tamingAttribute = this.getControllableEntity().getMind().getAttribute(TamingAttribute.class);
        if (tamingAttribute != null) {
            return tamingAttribute.isTamed();
        }
        return false;
    }

    private EntityLiving getTamer() {
        if (this.getHandle() instanceof EntityTameableAnimal) {
            return ((EntityTameableAnimal) this.getHandle()).getOwner();
        }
        TamingAttribute tamingAttribute = this.getControllableEntity().getMind().getAttribute(TamingAttribute.class);
        if (tamingAttribute != null) {
            return ((CraftLivingEntity) tamingAttribute.getTamer()).getHandle();
        }
        return null;
    }

    @Override
    public boolean shouldStart() {
        EntityLiving tamer = this.getTamer();

        if (tamer == null) {
            return false;
        } else if (this.isSitting()) {
            return false;
        } else if (this.getHandle().e(tamer) < (double) (this.minDistance * this.minDistance)) {
            return false;
        } else {
            this.tamer = tamer;
            return true;
        }
    }

    @Override
    public boolean shouldContinue() {
        return !NMSEntityUtil.getNavigation(this.getHandle()).g() && this.getHandle().e(this.tamer) > (double) (this.maxDistance * this.maxDistance) && !this.isSitting();
    }

    @Override
    public void start() {
        this.followTicks = 0;
        this.waterAvoidance = NMSEntityUtil.getNavigation(this.getHandle()).a();
        NMSEntityUtil.getNavigation(this.getHandle()).a(false);
    }

    @Override
    public void finish() {
        this.tamer = null;
        NMSEntityUtil.getNavigation(this.getHandle()).h();
        NMSEntityUtil.getNavigation(this.getHandle()).a(this.waterAvoidance);
    }

    @Override
    public void tick() {
        NMSEntityUtil.getControllerLook(this.getHandle()).a(this.tamer, 10.0F, (float) NMSEntityUtil.getMaxHeadRotation(this.getHandle()));
        if (!(this.isSitting())) {
            if (--this.followTicks <= 0) {
                this.followTicks = 10;
                if (!this.getControllableEntity().navigateTo((LivingEntity) this.tamer.getBukkitEntity(), this.navigationSpeed > 0 ? this.navigationSpeed : this.getControllableEntity().getSpeed())) {
                    if (!this.isLeashed()) {
                        if (this.getHandle().e(this.tamer) >= 144.0D) {
                            int x = MathHelper.floor(this.tamer.locX) - 2;
                            int z = MathHelper.floor(this.tamer.locZ) - 2;
                            int y = MathHelper.floor(this.tamer.boundingBox.b);

                            for (int l = 0; l <= 4; ++l) {
                                for (int i1 = 0; i1 <= 4; ++i1) {
                                    if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && World.a((IBlockAccess) this.getHandle().world, x + l, y - 1, z + i1) && !this.getHandle().world.getType(x + l, y, z + i1).r() && !this.getHandle().world.getType(x + l, y + 1, z + i1).r()) {
                                        this.getHandle().setPositionRotation((double) ((float) (x + l) + 0.5F), (double) y, (double) ((float) (z + i1) + 0.5F), this.getHandle().yaw, this.getHandle().pitch);
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
    }

    private boolean isSitting() {
        return this.getHandle() instanceof EntityTameableAnimal && ((EntityTameableAnimal) this.getHandle()).isSitting();
    }

    private boolean isLeashed() {
        return this.getHandle() instanceof EntityInsentient && ((EntityInsentient) this.getHandle()).bL();
    }
}