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

import org.bukkit.entity.Creeper;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Ocelot;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.mind.behaviour.goals.*;
import org.entityapi.api.entity.type.ControllableCreeper;
import org.entityapi.api.entity.type.nms.ControllableCreeperHandle;

public class ControllableCreeperBase extends ControllableBaseEntity<Creeper, ControllableCreeperHandle> implements ControllableCreeper {

    public ControllableCreeperBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.CREEPER, manager);
    }

    public ControllableCreeperBase(int id, ControllableCreeperHandle entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
    }

    @Override
    public void explode(int modifier) {
        if (this.handle != null) {
            this.handle.explode(modifier);
        }
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.creeper.say");
        this.setSound(EntitySound.DEATH, "mob.creeper.death");
    }

    @Override
    public BehaviourItem[] getDefaultTargetingBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(1, new BehaviourFloat(this)),
                new BehaviourItem(2, new BehaviourSwell(this)),
                new BehaviourItem(3, new BehaviourAvoidEntity(this, Ocelot.class, 6.0F, 1.0D, 1.2D)),
                new BehaviourItem(4, new BehaviourMeleeAttack(this, false, 1.0D)),
                new BehaviourItem(5, new BehaviourRandomStroll(this, 0.8D)),
                new BehaviourItem(6, new BehaviourLookAtNearestEntity(this, HumanEntity.class, 8F)),
                new BehaviourItem(7, new BehaviourLookAtRandom(this))
        };
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(1, new BehaviourMoveTowardsNearestAttackableTarget(this, HumanEntity.class, 0, true)),
                new BehaviourItem(2, new BehaviourHurtByTarget(this, false))
        };
    }
}