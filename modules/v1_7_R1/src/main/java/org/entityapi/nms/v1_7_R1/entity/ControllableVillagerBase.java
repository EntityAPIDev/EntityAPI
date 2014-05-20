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

package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.mind.behaviour.goals.*;
import org.entityapi.api.entity.type.ControllableVillager;
import org.entityapi.api.entity.type.bukkit.InsentientEntity;

public class ControllableVillagerBase extends ControllableBaseEntity<Villager, ControllableVillagerEntity> implements ControllableVillager {

    public ControllableVillagerBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.VILLAGER, manager);
    }

    public ControllableVillagerBase(int id, ControllableVillagerEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "haggle", "mob.villager.haggle");
        this.setSound(EntitySound.IDLE, "idle", "mob.villager.idle");
        this.setSound(EntitySound.HURT, "mob.villager.hit");
        this.setSound(EntitySound.DEATH, "mob.villager.death");
        this.setSound(EntitySound.ACCEPT, "mob.villager.yes");
        this.setSound(EntitySound.DENY, "mob.villager.no");
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(0, new BehaviourFloat(this)),
                new BehaviourItem(1, new BehaviourAvoidEntity(this, Zombie.class, 8.0F, 0.6D, 0.6D)),
                new BehaviourItem(1, new BehaviourTradeWithPlayer(this)),
                new BehaviourItem(1, new BehaviourLookAtTradingPlayer(this)),
                new BehaviourItem(2, new BehaviourMoveIndoors(this)),
                new BehaviourItem(3, new BehaviourRestrictOpenDoor(this)),
                new BehaviourItem(4, new BehaviourOpenDoor(this, true)),
                new BehaviourItem(5, new BehaviourMoveTowardsRestriction(this, 0.6D)),
                new BehaviourItem(6, new BehaviourMakeLove(this)),
                new BehaviourItem(7, new BehaviourTakeFlower(this)),
                new BehaviourItem(8, new BehaviourVillagerPlay(this, 0.32D)),
                new BehaviourItem(9, new BehaviourInteract(this, HumanEntity.class, 3.0F, 1.0F)),
                new BehaviourItem(9, new BehaviourInteract(this, Villager.class, 3.0F, 1.0F)),
                new BehaviourItem(9, new BehaviourRandomStroll(this, 0.6D)),
                new BehaviourItem(10, new BehaviourLookAtNearestEntity(this, InsentientEntity.class, 8.0F))
        };
    }
}