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

package org.entityapi.nms.v1_8_R1.entity.selector;

import net.minecraft.server.v1_8_R1.*;

public class EntitySelectorEquipable implements IEntitySelector {

    private final ItemStack c;

    public EntitySelectorEquipable(ItemStack itemstack) {
        this.c = itemstack;
    }

    @Override
    public boolean a(Entity entity) {
        if (!entity.isAlive()) {
            return false;
        } else if (!(entity instanceof EntityLiving)) {
            return false;
        } else {
            EntityLiving entityliving = (EntityLiving) entity;

            return entityliving.getEquipment(EntityInsentient.b(this.c)) != null ? false : (entityliving instanceof EntityInsentient ? ((EntityInsentient) entityliving).bH() : entityliving instanceof EntityHuman);
        }
    }
}