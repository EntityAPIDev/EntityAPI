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

import org.bukkit.entity.Enderman;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.type.ControllableEnderman;
import org.entityapi.api.entity.type.nms.ControllableEndermanHandle;

public class ControllableEndermanBase extends ControllableBaseEntity<Enderman, ControllableEndermanHandle> implements ControllableEnderman {

    public ControllableEndermanBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.ENDERMAN, manager);
    }

    public ControllableEndermanBase(int id, ControllableEndermanHandle entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "idle", "mob.enderman.idle");
        this.setSound(EntitySound.IDLE, "scream", "mob.enderman.scream");
        this.setSound(EntitySound.HURT, "mob.enderman.hit");
        this.setSound(EntitySound.DEATH, "mob.enderman.death");
        this.setSound(EntitySound.STARE, "mob.enderman.stare");
        this.setSound(EntitySound.TELEPORT, "mob.enderman.portal");
    }
}