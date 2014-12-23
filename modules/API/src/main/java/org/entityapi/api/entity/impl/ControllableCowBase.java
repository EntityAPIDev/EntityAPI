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

import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.HumanEntity;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.mind.behaviour.goals.*;
import org.entityapi.api.entity.type.ControllableCow;
import org.entityapi.api.entity.type.nms.ControllableCowHandle;

public class ControllableCowBase extends ControllableBaseEntity<Cow, ControllableCowHandle> implements ControllableCow {

    public ControllableCowBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.COW, manager);
    }

    public ControllableCowBase(int id, ControllableCowHandle entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.cow.say");
        this.setSound(EntitySound.HURT, "mob.cow.hurt");
        this.setSound(EntitySound.DEATH, "mob.cow.hurt");
        this.setSound(EntitySound.STEP, "mob.cow.step");
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(0, new BehaviourFloat(this)),
                new BehaviourItem(1, new BehaviourPanic(this, 2.0D)),
                new BehaviourItem(2, new BehaviourBreed(this, 1.0D)),
                new BehaviourItem(3, new BehaviourTempt(this, Material.WHEAT, false, 1.25D)),
                new BehaviourItem(4, new BehaviourFollowParent(this, 1.25D)),
                new BehaviourItem(5, new BehaviourRandomStroll(this, 1.0D)),
                new BehaviourItem(6, new BehaviourLookAtNearestEntity(this, HumanEntity.class, 6.0F)),
                new BehaviourItem(7, new BehaviourLookAtRandom(this))
        };
    }
}