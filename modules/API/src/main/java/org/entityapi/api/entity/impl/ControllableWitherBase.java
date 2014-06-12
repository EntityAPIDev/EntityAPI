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
import org.bukkit.entity.Wither;
import org.entityapi.api.EntityManager;
import org.entityapi.api.NMSAccessor;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.mind.behaviour.goals.*;
import org.entityapi.api.entity.type.ControllableWither;
import org.entityapi.api.entity.type.bukkit.InsentientEntity;
import org.entityapi.api.entity.type.nms.ControllableWitherHandle;

public class ControllableWitherBase extends ControllableBaseEntity<Wither, ControllableWitherHandle> implements ControllableWither {

    public ControllableWitherBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.WITHER, manager);
    }

    public ControllableWitherBase(int id, ControllableWitherHandle entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.wither.idle");
        this.setSound(EntitySound.HURT, "mob.wither.hurt");
        this.setSound(EntitySound.DEATH, "mob.wither.death");
    }

    @Override
    public BehaviourItem[] getDefaultTargetingBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(0, new BehaviourFloat(this)),
                new BehaviourItem(2, new BehaviourRangedAttack(this, 40, 20.0F, 1.0D)),
                new BehaviourItem(5, new BehaviourRandomStroll(this, 1.0D)),
                new BehaviourItem(6, new BehaviourLookAtNearestEntity(this, HumanEntity.class, 8.0F)),
                new BehaviourItem(7, new BehaviourLookAtRandom(this))
        };
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(1, new BehaviourHurtByTarget(this, false)),
                new BehaviourItem(2, new BehaviourMoveTowardsNearestAttackableTarget(this, InsentientEntity.class, 0, false, false, getNMSAccessor().getSelector(NMSAccessor.SelectorType.NOT_UNDEAD)))
        };
    }
}