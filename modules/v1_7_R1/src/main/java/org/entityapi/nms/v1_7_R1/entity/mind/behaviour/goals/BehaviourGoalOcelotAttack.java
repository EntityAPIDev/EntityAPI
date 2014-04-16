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
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourGoalBase;

public class BehaviourGoalOcelotAttack extends BehaviourGoalBase {

    private EntityLiving target;
    private int attackTicks;

    public BehaviourGoalOcelotAttack(ControllableEntity controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ACTION;
    }

    @Override
    public String getDefaultKey() {
        return "Ocelot Attack";
    }

    @Override
    public boolean shouldStart() {
        EntityLiving target = ((CraftLivingEntity) this.getControllableEntity().getTarget()).getHandle();

        if (target == null) {
            return false;
        } else {
            this.target = target;
            return true;
        }
    }

    @Override
    public boolean shouldContinue() {
        return !this.target.isAlive() ? false : (this.getHandle().e(this.target) > 225.0D ? false : !NMSEntityUtil.getNavigation(this.getHandle()).g() || this.shouldStart());
    }

    @Override
    public void finish() {
        this.target = null;
        NMSEntityUtil.getNavigation(this.getHandle()).h();
    }

    @Override
    public void tick() {
        NMSEntityUtil.getControllerLook(this.getHandle()).a(this.target, 30.0F, 30.0F);
        double minDistance = (double) (this.getHandle().width * 2.0F * this.getHandle().width * 2.0F);
        double distance = this.getHandle().e(this.target.locX, this.target.boundingBox.b, this.target.locZ);
        double navigationSpeed = 0.8D;

        if (distance > minDistance && distance < 16.0D) {
            navigationSpeed = 1.33D;
        } else if (distance < 225.0D) {
            navigationSpeed = 0.6D;
        }

        this.getControllableEntity().navigateTo((LivingEntity) this.target.getBukkitEntity(), navigationSpeed);
        this.attackTicks = Math.max(this.attackTicks - 1, 0);
        if (distance <= minDistance) {
            if (this.attackTicks <= 0) {
                this.attackTicks = 20;
                this.getHandle().m(this.target);
            }
        }
    }
}