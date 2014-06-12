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
import org.bukkit.entity.Chicken;
import org.bukkit.entity.HumanEntity;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.mind.behaviour.goals.*;
import org.entityapi.api.entity.type.ControllableChicken;
import org.entityapi.api.entity.type.nms.ControllableChickenHandle;

public class ControllableChickenBase extends ControllableBaseEntity<Chicken, ControllableChickenHandle> implements ControllableChicken {

    public ControllableChickenBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.CHICKEN, manager);
    }

    public ControllableChickenBase(int id, ControllableChickenHandle entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.chicken.idle");
        this.setSound(EntitySound.HURT, "mob.chicken.hurt");
        this.setSound(EntitySound.DEATH, "mob.chicken.hurt");
        this.setSound(EntitySound.STEP, "mob.chicken.step");
        this.setSound(EntitySound.LAY_EGG, "mob.chicken.plop");
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(0, new BehaviourFloat(this)),
                new BehaviourItem(1, new BehaviourPanic(this, 1.4D)),
                new BehaviourItem(2, new BehaviourBreed(this, 1.0D)),
                new BehaviourItem(3, new BehaviourTempt(this, Material.SEEDS, false, 1.0D)),
                new BehaviourItem(4, new BehaviourFollowParent(this, 1.1D)),
                new BehaviourItem(5, new BehaviourRandomStroll(this, 1.0D)),
                new BehaviourItem(6, new BehaviourLookAtNearestEntity(this, HumanEntity.class, 6.0F)),
                new BehaviourItem(7, new BehaviourLookAtRandom(this))
        };
    }
}