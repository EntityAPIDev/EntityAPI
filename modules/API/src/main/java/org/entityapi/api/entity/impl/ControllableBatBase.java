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

import org.bukkit.entity.Bat;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.type.ControllableBat;
import org.entityapi.api.entity.type.nms.ControllableBatHandle;

public class ControllableBatBase extends ControllableBaseEntity<Bat, ControllableBatHandle> implements ControllableBat {

    public ControllableBatBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.BAT, manager);
    }

    public ControllableBatBase(int id, ControllableBatHandle entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void setHanging(boolean flag) {
        this.handle.setHanging(flag);
    }

    @Override
    public boolean isHanging() {
        return this.handle.isHanging();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.bat.idle");
        this.setSound(EntitySound.HURT, "mob.bat.hurt");
        this.setSound(EntitySound.DEATH, "mob.bat.death");
    }
}