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

import org.bukkit.entity.Blaze;
import org.bukkit.entity.HumanEntity;
import org.entityapi.api.EntityManager;
import org.entityapi.api.ProjectileType;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.mind.behaviour.goals.BehaviourHurtByTarget;
import org.entityapi.api.entity.mind.behaviour.goals.BehaviourMoveTowardsNearestAttackableTarget;
import org.entityapi.api.entity.mind.behaviour.goals.BehaviourRangedAttack;
import org.entityapi.api.entity.type.ControllableBlaze;
import org.entityapi.api.entity.type.nms.ControllableBlazeHandle;

public class ControllableBlazeBase extends ControllableBaseEntity<Blaze, ControllableBlazeHandle> implements ControllableBlaze {

    public ControllableBlazeBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.BLAZE, manager);
    }

    public ControllableBlazeBase(int id, ControllableBlazeHandle entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.blaze.breathe");
        this.setSound(EntitySound.HURT, "mob.blaze.hit");
        this.setSound(EntitySound.DEATH, "mob.blaze.death");
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(0, new BehaviourRangedAttack(this, ProjectileType.SMALL_FIREBALL, 20, 8))
        };
    }

    @Override
    public BehaviourItem[] getDefaultTargetingBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(1, new BehaviourHurtByTarget(this, true, false, true)),
                new BehaviourItem(2, new BehaviourMoveTowardsNearestAttackableTarget(this, HumanEntity.class, 0, true))
        };
    }
}