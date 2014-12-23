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

import net.minecraft.server.v1_8_R1.EntityTameableAnimal;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.attribute.TamingAttribute;

public class BehaviourGoalRandomTargetNonTamed extends BehaviourGoalMoveTowardsNearestAttackableTarget {

    public BehaviourGoalRandomTargetNonTamed(ControllableEntity controllableEntity, Class<? extends org.bukkit.entity.Entity> classToTarget, int chance, boolean checkSenses) {
        super(controllableEntity, classToTarget, chance, checkSenses, false, null);
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

    @Override
    public String getDefaultKey() {
        return "Random Target Non Tamed";
    }

    @Override
    public boolean shouldStart() {
        return !this.isTamed() && super.shouldStart();
    }
}