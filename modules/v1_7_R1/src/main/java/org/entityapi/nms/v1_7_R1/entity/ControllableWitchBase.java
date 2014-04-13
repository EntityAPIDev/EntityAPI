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
import org.bukkit.entity.Witch;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.type.ControllableWitch;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals.*;

public class ControllableWitchBase extends ControllableBaseEntity<Witch, ControllableWitchEntity> implements ControllableWitch {

    public ControllableWitchBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.WITCH, manager);
    }

    public ControllableWitchBase(int id, ControllableWitchEntity entityHandle, EntityManager manager) {
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
                new BehaviourItem(new BehaviourFloat(this), 1),
                new BehaviourItem(new BehaviourRangedAttack(this, 60, 10.0F, 1.0D), 2),
                new BehaviourItem(new BehaviourRandomStroll(this, 1.0D), 2),
                new BehaviourItem(new BehaviourLookAtNearestEntity(this, HumanEntity.class, 8.0F), 3),
                new BehaviourItem(new BehaviourLookAtRandom(this), 3)
        };
    }

    @Override
    public BehaviourItem[] getDefaultTargetingBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(new BehaviourHurtByTarget(this, false), 1),
                new BehaviourItem(new BehaviourMoveTowardsNearestAttackableTarget(this, HumanEntity.class, 0, true), 2)
        };
    }
}