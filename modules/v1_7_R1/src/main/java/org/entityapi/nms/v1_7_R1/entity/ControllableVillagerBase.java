/*
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
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.type.ControllableVillager;
import org.entityapi.api.entity.type.bukkit.InsentientEntity;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals.*;

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
        this.setSound(EntitySound.YES, "mob.villager.yes");
        this.setSound(EntitySound.NO, "mob.villager.no");
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[] {
                new BehaviourItem(new BehaviourFloat(this), 0),
                new BehaviourItem(new BehaviourAvoidEntity(this, Zombie.class, 8.0F, 0.6D, 0.6D), 1),
                new BehaviourItem(new BehaviourTradeWithPlayer(this), 1),
                new BehaviourItem(new BehaviourLookAtTradingPlayer(this), 1),
                new BehaviourItem(new BehaviourMoveIndoors(this), 2),
                new BehaviourItem(new BehaviourRestrictOpenDoor(this), 3),
                new BehaviourItem(new BehaviourOpenDoor(this, true), 4),
                new BehaviourItem(new BehaviourMoveTowardsRestriction(this, 0.6D), 5),
                new BehaviourItem(new BehaviourMakeLove(this), 6),
                new BehaviourItem(new BehaviourTakeFlower(this), 7),
                new BehaviourItem(new BehaviourVillagerPlay(this, 0.32D), 8),
                new BehaviourItem(new BehaviourInteract(this, HumanEntity.class, 3.0F, 1.0F), 9),
                new BehaviourItem(new BehaviourInteract(this, Villager.class, 3.0F, 1.0F), 9),
                new BehaviourItem(new BehaviourRandomStroll(this, 0.6D), 9),
                new BehaviourItem(new BehaviourLookAtNearestEntity(this, InsentientEntity.class, 8.0F), 10)
        };
    }
}