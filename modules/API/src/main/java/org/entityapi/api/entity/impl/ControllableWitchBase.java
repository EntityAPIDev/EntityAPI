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
import org.bukkit.entity.Witch;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.mind.behaviour.goals.*;
import org.entityapi.api.entity.type.ControllableWitch;
import org.entityapi.api.entity.type.nms.ControllableWitchHandle;

public class ControllableWitchBase extends ControllableBaseEntity<Witch, ControllableWitchHandle> implements ControllableWitch {

    public ControllableWitchBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.WITCH, manager);
    }

    public ControllableWitchBase(int id, ControllableWitchHandle entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.witch.idle");
        this.setSound(EntitySound.HURT, "mob.witch.hurt");
        this.setSound(EntitySound.DEATH, "mob.witch.death");
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(1, new BehaviourFloat(this)),
                new BehaviourItem(2, new BehaviourRangedAttack(this, 60, 10.0F, 1.0D)),
                new BehaviourItem(2, new BehaviourRandomStroll(this, 1.0D)),
                new BehaviourItem(3, new BehaviourLookAtNearestEntity(this, HumanEntity.class, 8.0F)),
                new BehaviourItem(3, new BehaviourLookAtRandom(this))
        };
    }

    @Override
    public BehaviourItem[] getDefaultTargetingBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(1, new BehaviourHurtByTarget(this, false)),
                new BehaviourItem(2, new BehaviourMoveTowardsNearestAttackableTarget(this, HumanEntity.class, 0, true))
        };
    }
}