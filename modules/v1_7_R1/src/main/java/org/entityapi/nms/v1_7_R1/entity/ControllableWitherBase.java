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
import org.bukkit.entity.Wither;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.type.ControllableWither;
import org.entityapi.api.entity.type.bukkit.InsentientEntity;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals.*;
import org.entityapi.nms.v1_7_R1.entity.selector.EntitySelectorNotUndead;

public class ControllableWitherBase extends ControllableBaseEntity<Wither, ControllableWitherEntity> implements ControllableWither {

    public ControllableWitherBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.WITHER, manager);
    }

    public ControllableWitherBase(int id, ControllableWitherEntity entityHandle, EntityManager manager) {
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
                new BehaviourItem(new BehaviourFloat(this), 0),
                new BehaviourItem(new BehaviourRangedAttack(this, 40, 20.0F, 1.0D), 2),
                new BehaviourItem(new BehaviourRandomStroll(this, 1.0D), 5),
                new BehaviourItem(new BehaviourLookAtNearestEntity(this, HumanEntity.class, 8.0F), 6),
                new BehaviourItem(new BehaviourLookAtRandom(this), 7)
        };
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(new BehaviourHurtByTarget(this, false), 1),
                new BehaviourItem(new BehaviourMoveTowardsNearestAttackableTarget(this, InsentientEntity.class, 0, false, false, new EntitySelectorNotUndead()), 2)
        };
    }
}