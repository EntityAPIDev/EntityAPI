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

package org.entityapi.nms.v1_8_R1.entity.mind.behaviour.goals;

import net.minecraft.server.v1_8_R1.EntityLiving;
import net.minecraft.server.v1_8_R1.EntityTameableAnimal;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftLivingEntity;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.attribute.TamingAttribute;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_8_R1.NMSEntityUtil;
import org.entityapi.nms.v1_8_R1.entity.mind.behaviour.BehaviourGoalBase;

public class BehaviourGoalSit extends BehaviourGoalBase {

    private boolean willSit;

    public BehaviourGoalSit(ControllableEntity controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.IMPULSE;
    }

    @Override
    public String getDefaultKey() {
        return "Sit";
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

    private void setSitting(boolean flag) {
        if (this.getHandle() instanceof EntityTameableAnimal) {
            ((EntityTameableAnimal) this.getHandle()).setSitting(true);
        } else {
            this.getControllableEntity().setStationary(flag);
        }
        this.willSit = flag;
    }

    @Override
    public boolean shouldStart() {
        if (!isTamed()) {
            return this.willSit && this.getControllableEntity().getTarget() == null; // CraftBukkit - Allow sitting for wild animals
        } else if (this.getHandle().M()) {
            return false;
        } else if (!this.getHandle().onGround) {
            return false;
        } else {
            EntityLiving owner = this.getTamer();

            return owner == null ? true : (this.getHandle().e(owner) < 144.0D && owner.getLastDamager() != null ? false : this.willSit);
        }
    }

    @Override
    public void start() {
        NMSEntityUtil.getNavigation(this.getHandle()).h();
        this.setSitting(true);
    }

    @Override
    public void finish() {
        this.setSitting(false);
    }

    @Override
    public void tick() {

    }
}