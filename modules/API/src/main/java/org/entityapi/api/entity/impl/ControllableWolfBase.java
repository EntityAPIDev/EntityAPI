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
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Wolf;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.mind.behaviour.goals.*;
import org.entityapi.api.entity.type.ControllableWolf;
import org.entityapi.api.entity.type.nms.ControllableWolfHandle;

public class ControllableWolfBase extends ControllableBaseEntity<Wolf, ControllableWolfHandle> implements ControllableWolf {

    public ControllableWolfBase(int id, EntityManager entityManager) {
        super(id, ControllableEntityType.WOLF, entityManager);
    }

    public ControllableWolfBase(int id, ControllableWolfHandle entityHandle, EntityManager entityManager) {
        this(id, entityManager);
        this.handle = entityHandle;
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "growl", "mob.wolf.growl");
        this.setSound(EntitySound.IDLE, "whine", "mob.wolf.whine");
        this.setSound(EntitySound.IDLE, "panting", "mob.wolf.panting");
        this.setSound(EntitySound.IDLE, "bark", "mob.wolf.bark");
        this.setSound(EntitySound.HURT, "mob.wolf.hurt");
        this.setSound(EntitySound.DEATH, "mob.wolf.death");
        this.setSound(EntitySound.STEP, "mob.wolf.step");
        this.setSound(EntitySound.SHAKE, "mob.wolf.shake");
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(1, new BehaviourFloat(this)),
                new BehaviourItem(2, new BehaviourSit(this)),
                new BehaviourItem(3, new BehaviourLeapAtTarget(this, 0.4F)),
                new BehaviourItem(4, new BehaviourMeleeAttack(this, true, 1.0D)),
                new BehaviourItem(5, new BehaviourFollowTamer(this, 10.0F, 2.0F, 1.0D)),
                new BehaviourItem(6, new BehaviourBreed(this, 1.0D)),
                new BehaviourItem(7, new BehaviourRandomStroll(this, 1.0D)),
                new BehaviourItem(8, new BehaviourBeg(this, new Material[]{Material.BONE})),
                new BehaviourItem(9, new BehaviourLookAtNearestEntity(this, HumanEntity.class, 8.0F)),
                new BehaviourItem(9, new BehaviourLookAtRandom(this))
        };
    }

    @Override
    public BehaviourItem[] getDefaultTargetingBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(1, new BehaviourHelpTamerTarget(this)),
                new BehaviourItem(2, new BehaviourDefendTamer(this)),
                new BehaviourItem(3, new BehaviourHurtByTarget(this, true)),
                new BehaviourItem(4, new BehaviourRandomTargetNonTamed(this, Sheep.class, 200, false))
        };
    }
}