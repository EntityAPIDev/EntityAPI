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

import org.bukkit.entity.Slime;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.type.ControllableSlime;
import org.entityapi.api.entity.type.nms.ControllableSlimeHandle;

public class ControllableSlimeBase extends ControllableBaseEntity<Slime, ControllableSlimeHandle> implements ControllableSlime {

    public ControllableSlimeBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.SLIME, manager);
    }

    public ControllableSlimeBase(int id, ControllableSlimeHandle entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultLoot();
    }

    @Override
    public void initSounds() {
        for (EntitySound type : new EntitySound[]{EntitySound.IDLE, EntitySound.HURT, EntitySound.DEATH}) {
            this.setSound(type, "mob.slime.big", "big");
            this.setSound(type, "mob.slime.small", "small");
        }
        this.setSound(EntitySound.ATTACK, "mob.attack");
    }
}