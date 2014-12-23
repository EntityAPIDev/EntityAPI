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
import org.bukkit.entity.LivingEntity;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.attribute.TamingAttribute;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;

public class BehaviourGoalDefendTamer extends BehaviourGoalTarget {

    private EntityLiving target;
    private int lastAttackTick;

    public BehaviourGoalDefendTamer(ControllableEntity controllableEntity) {
        super(controllableEntity, false);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.INSTINCT;
    }

    @Override
    public String getDefaultKey() {
        return "Defend Tamer";
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
        if (!this.isTamed()) {
            return false;
        } else {
            EntityLiving tamer = this.getTamer();

            if (tamer == null) {
                return false;
            } else {
                this.target = tamer.getLastDamager();
                int lastAttackTick = tamer.aK();

                return lastAttackTick != this.lastAttackTick && this.isSuitableTarget(this.target, false);
            }
        }
    }

    @Override
    public void tick() {
        this.getControllableEntity().setTarget((LivingEntity) this.target.getBukkitEntity());

        EntityLiving tamer = this.getTamer();
        if (tamer != null) {
            this.lastAttackTick = tamer.aK();
        }

        super.tick();
    }
}