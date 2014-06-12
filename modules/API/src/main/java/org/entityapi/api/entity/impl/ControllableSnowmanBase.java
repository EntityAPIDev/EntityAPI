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

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Snowman;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.mind.behaviour.goals.*;
import org.entityapi.api.entity.type.ControllableSnowman;
import org.entityapi.api.entity.type.bukkit.InsentientEntity;
import org.entityapi.api.entity.type.nms.ControllableSnowmanHandle;

public class ControllableSnowmanBase extends ControllableBaseEntity<Snowman, ControllableSnowmanHandle> implements ControllableSnowman {

    public ControllableSnowmanBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.SLIME, manager);
    }

    public ControllableSnowmanBase(int id, ControllableSnowmanHandle entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public BehaviourItem[] getDefaultTargetingBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(1, new BehaviourRangedAttack(this, 20, 10.0F, 1.25)),
                new BehaviourItem(2, new BehaviourRandomStroll(this, 1.0D)),
                new BehaviourItem(3, new BehaviourLookAtNearestEntity(this, HumanEntity.class, 6.0F)),
                new BehaviourItem(4, new BehaviourLookAtRandom(this)),
        };
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(1, new BehaviourMoveTowardsNearestAttackableTarget(this, InsentientEntity.class, 0, true, false, getNMSAccessor().getMonsterSelector()))
        };
    }
}