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

import org.bukkit.entity.Blaze;
import org.bukkit.entity.HumanEntity;
import org.entityapi.api.EntityManager;
import org.entityapi.api.ProjectileType;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.type.ControllableBlaze;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals.BehaviourHurtByTarget;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals.BehaviourMoveTowardsNearestAttackableTarget;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals.BehaviourRangedAttack;

public class ControllableBlazeBase extends ControllableBaseEntity<Blaze, ControllableBlazeEntity> implements ControllableBlaze {

    public ControllableBlazeBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.BLAZE, manager);
    }

    public ControllableBlazeBase(int id, ControllableBlazeEntity entityHandle, EntityManager manager) {
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
                new BehaviourItem(new BehaviourRangedAttack(this, ProjectileType.SMALL_FIREBALL, 20, 8), 0)
        };
    }

    @Override
    public BehaviourItem[] getDefaultTargetingBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(new BehaviourHurtByTarget(this, true, false, true), 1),
                new BehaviourItem(new BehaviourMoveTowardsNearestAttackableTarget(this, HumanEntity.class, 0, true), 2)
        };
    }
}