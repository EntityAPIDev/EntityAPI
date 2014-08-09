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
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Pig;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.mind.behaviour.goals.*;
import org.entityapi.api.entity.type.ControllablePig;
import org.entityapi.api.entity.type.nms.ControllablePigHandle;

public class ControllablePigBase extends ControllableBaseEntity<Pig, ControllablePigHandle> implements ControllablePig {

    public ControllablePigBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.PIG, manager);
    }

    public ControllablePigBase(int id, ControllablePigHandle entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.pig.say");
        this.setSound(EntitySound.HURT, "mob.pig.say");
        this.setSound(EntitySound.DEATH, "mob.pig.death");
        this.setSound(EntitySound.STEP, "mob.pig.step");
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(0, new BehaviourFloat(this)),
                new BehaviourItem(1, new BehaviourPanic(this, 1.25D)),
                new BehaviourItem(2, new BehaviourFollowCarrotStick(this, 0.3F)),
                new BehaviourItem(3, new BehaviourBreed(this, 1.0D)),
                new BehaviourItem(4, new BehaviourTempt(this, Material.CARROT, false)),
                new BehaviourItem(4, new BehaviourTempt(this, Material.CARROT_STICK, false)),
                new BehaviourItem(5, new BehaviourFollowParent(this, 1.1D)),
                new BehaviourItem(6, new BehaviourRandomStroll(this, 1.0D)),
                new BehaviourItem(7, new BehaviourLookAtNearestEntity(this, HumanEntity.class, 6.0F)),
                new BehaviourItem(8, new BehaviourLookAtRandom(this))
        };
    }
}