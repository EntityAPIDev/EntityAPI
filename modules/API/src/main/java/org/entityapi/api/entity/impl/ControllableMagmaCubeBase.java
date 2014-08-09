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

package org.entityapi.api.entity.impl;

import org.bukkit.entity.MagmaCube;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.type.ControllableMagmaCube;
import org.entityapi.api.entity.type.nms.ControllableMagmaCubeHandle;

public class ControllableMagmaCubeBase extends ControllableBaseEntity<MagmaCube, ControllableMagmaCubeHandle> implements ControllableMagmaCube {

    public ControllableMagmaCubeBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.LAVA_SLIME, manager);
    }

    public ControllableMagmaCubeBase(int id, ControllableMagmaCubeHandle entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultLoot();
    }

    @Override
    public void initSounds() {
        for (EntitySound type : new EntitySound[]{EntitySound.IDLE, EntitySound.HURT, EntitySound.DEATH}) {
            this.setSound(type, "mob." + (type == EntitySound.IDLE ? "magmacube" : "slime") + ".big", "big");
            this.setSound(type, "mob." + (type == EntitySound.IDLE ? "magmacube" : "slime") + ".small", "small");
        }
        this.setSound(EntitySound.ATTACK, "mob.attack");
    }
}