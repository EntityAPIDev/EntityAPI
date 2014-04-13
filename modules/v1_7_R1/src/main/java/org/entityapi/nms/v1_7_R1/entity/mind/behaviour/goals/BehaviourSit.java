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
import net.minecraft.server.v1_7_R1.EntityTameableAnimal;
import org.bukkit.entity.Animals;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.BasicEntityUtil;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;

public class BehaviourSit extends BehaviourBase {

    private boolean willSit;

    public BehaviourSit(ControllableEntity<? extends Animals> controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public ControllableEntity<? extends Animals> getControllableEntity() {
        return super.getControllableEntity();
    }

    @Override
    public EntityTameableAnimal getHandle() {
        return (EntityTameableAnimal) BasicEntityUtil.getInstance().getHandle(this.getControllableEntity());
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.IMPULSE;
    }

    @Override
    public String getDefaultKey() {
        return "Sit";
    }

    @Override
    public boolean shouldStart() {
        if (!this.getHandle().isTamed()) {
            return this.willSit && this.getHandle().getGoalTarget() == null; // CraftBukkit - Allow sitting for wild animals
        } else if (this.getHandle().M()) {
            return false;
        } else if (!this.getHandle().onGround) {
            return false;
        } else {
            EntityLiving owner = this.getHandle().getOwner();

            return owner == null ? true : (this.getHandle().e(owner) < 144.0D && owner.getLastDamager() != null ? false : this.willSit);
        }
    }

    @Override
    public void start() {
        NMSEntityUtil.getNavigation(this.getHandle()).h();
        this.getHandle().setSitting(true);
    }

    @Override
    public void finish() {
        this.getHandle().setSitting(false);
    }

    public void setSitting(boolean flag) {
        this.willSit = flag;
    }

    @Override
    public void tick() {

    }
}