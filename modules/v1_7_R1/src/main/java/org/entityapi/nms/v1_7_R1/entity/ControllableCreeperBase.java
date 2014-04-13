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

import org.bukkit.entity.Creeper;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Ocelot;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.type.ControllableCreeper;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals.*;

public class ControllableCreeperBase extends ControllableBaseEntity<Creeper, ControllableCreeperEntity> implements ControllableCreeper {

    public ControllableCreeperBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.CREEPER, manager);
    }

    public ControllableCreeperBase(int id, ControllableCreeperEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
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
        return new BehaviourItem[] {
                new BehaviourItem(new BehaviourFloat(this), 1),
                new BehaviourItem(new BehaviourSwell(this), 2),
                new BehaviourItem(new BehaviourAvoidEntity(this, Ocelot.class, 6.0F, 1.0D, 1.2D), 3),
                new BehaviourItem(new BehaviourMeleeAttack(this, false, 1.0D), 4),
                new BehaviourItem(new BehaviourRandomStroll(this, 0.8D), 5),
                new BehaviourItem(new BehaviourLookAtNearestEntity(this, HumanEntity.class, 8F), 6),
                new BehaviourItem(new BehaviourLookAtRandom(this), 7)
        };
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[] {
                new BehaviourItem(new BehaviourMoveTowardsNearestAttackableTarget(this, HumanEntity.class, 0, true), 1),
                new BehaviourItem(new BehaviourHurtByTarget(this, false), 2)
        };
    }
}