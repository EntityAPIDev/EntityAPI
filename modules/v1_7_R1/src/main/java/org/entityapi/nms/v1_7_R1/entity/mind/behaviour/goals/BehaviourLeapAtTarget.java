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

import net.minecraft.server.v1_7_R1.EntityLiving;
import net.minecraft.server.v1_7_R1.MathHelper;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;

public class BehaviourLeapAtTarget extends BehaviourBase {

    private EntityLiving target;
    private float jumpHeight;

    public BehaviourLeapAtTarget(ControllableEntity controllableEntity) {
        this(controllableEntity, 0.4F);
    }

    public BehaviourLeapAtTarget(ControllableEntity controllableEntity, float jumpHeight) {
        super(controllableEntity);
        this.jumpHeight = jumpHeight;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.FIVE;
    }

    @Override
    public String getDefaultKey() {
        return "Leap Target";
    }

    @Override
    public boolean shouldStart() {
        this.target = ((CraftLivingEntity) this.getControllableEntity().getTarget()).getHandle();
        if (this.target == null) {
            return false;
        } else {
            double d0 = this.getHandle().e(this.target);

            return d0 >= 4.0D && d0 <= 16.0D ? (!this.getHandle().onGround ? false : this.getHandle().aI().nextInt(5) == 0) : false;
        }
    }

    @Override
    public boolean shouldContinue() {
        return !this.getHandle().onGround;
    }

    @Override
    public void tick() {
        double diffX = this.target.locX - this.getHandle().locX;
        double diffZ = this.target.locZ - this.getHandle().locZ;
        float dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);

        this.getHandle().motX += diffX / (double) dist * 0.5D * 0.800000011920929D + this.getHandle().motX * 0.20000000298023224D;
        this.getHandle().motZ += diffZ / (double) dist * 0.5D * 0.800000011920929D + this.getHandle().motZ * 0.20000000298023224D;
        this.getHandle().motY = (double) this.jumpHeight;
    }
}