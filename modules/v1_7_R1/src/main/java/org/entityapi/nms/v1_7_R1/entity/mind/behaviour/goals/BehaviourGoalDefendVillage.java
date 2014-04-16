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

import net.minecraft.server.v1_7_R1.EntityIronGolem;
import net.minecraft.server.v1_7_R1.EntityLiving;
import net.minecraft.server.v1_7_R1.MathHelper;
import net.minecraft.server.v1_7_R1.Village;
import org.bukkit.entity.LivingEntity;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;

public class BehaviourGoalDefendVillage extends BehaviourGoalTarget {

    private EntityLiving target;

    public BehaviourGoalDefendVillage(ControllableEntity controllableEntity) {
        super(controllableEntity, false, true);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.INSTINCT;
    }

    @Override
    public String getDefaultKey() {
        return "Defend Village";
    }

    @Override
    public boolean shouldStart() {
        Village village;
        if (this.getHandle() instanceof EntityIronGolem) {
            village = ((EntityIronGolem) this.getHandle()).bX();
        } else {
            village = this.getHandle().world.villages.getClosestVillage(MathHelper.floor(this.getHandle().locX), MathHelper.floor(this.getHandle().locY), MathHelper.floor(this.getHandle().locZ), 32);
        }

        if (village == null) {
            return false;
        } else {
            this.target = village.b(this.getHandle());
            if (!this.isSuitableTarget(this.target, false)) {
                if (this.getHandle().aI().nextInt(20) == 0) {
                    this.target = village.c(this.getHandle());
                    return this.isSuitableTarget(this.target, false);
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    @Override
    public void tick() {
        this.getControllableEntity().setTarget((LivingEntity) this.getHandle().getBukkitEntity());
        super.tick();
    }
}