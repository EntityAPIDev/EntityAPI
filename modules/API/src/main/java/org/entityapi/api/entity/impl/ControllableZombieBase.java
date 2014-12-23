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
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.mind.behaviour.goals.*;
import org.entityapi.api.entity.type.ControllableZombie;
import org.entityapi.api.entity.type.nms.ControllableZombieHandle;

public class ControllableZombieBase extends ControllableBaseEntity<Zombie, ControllableZombieHandle> implements ControllableZombie {

    public ControllableZombieBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.ZOMBIE, manager);
    }

    public ControllableZombieBase(int id, ControllableZombieHandle entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.zombie.say");
        this.setSound(EntitySound.HURT, "mob.zombie.hurt");
        this.setSound(EntitySound.DEATH, "mob.zombie.death");
        this.setSound(EntitySound.STEP, "mob.zombie.step");
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(0, new BehaviourFloat(this)),
                new BehaviourItem(1, new BehaviourBreakDoor(this, false)),
                new BehaviourItem(2, new BehaviourMeleeAttack(this, HumanEntity.class, false, 1.0D)),
                new BehaviourItem(4, new BehaviourMeleeAttack(this, Villager.class, true, 1.0D)),
                new BehaviourItem(5, new BehaviourMoveTowardsRestriction(this, 1.0D)),
                new BehaviourItem(6, new BehaviourMoveThroughVillage(this, false, 1.0D)),
                new BehaviourItem(7, new BehaviourRandomStroll(this, 1.0D)),
                new BehaviourItem(8, new BehaviourLookAtNearestEntity(this, HumanEntity.class, 8.0F)),
                new BehaviourItem(8, new BehaviourLookAtRandom(this))
        };
    }

    @Override
    public BehaviourItem[] getDefaultTargetingBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(1, new BehaviourHurtByTarget(this, true)),
                new BehaviourItem(2, new BehaviourMoveTowardsNearestAttackableTarget(this, HumanEntity.class, 0, true)),
                new BehaviourItem(2, new BehaviourMoveTowardsNearestAttackableTarget(this, Villager.class, 0, false))
        };
    }
}