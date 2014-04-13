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
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Villager;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.type.ControllablePigZombie;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals.*;

public class ControllablePigZombieBase extends ControllableBaseEntity<PigZombie, ControllablePigZombieEntity> implements ControllablePigZombie {

    public ControllablePigZombieBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.PIG_ZOMBIE, manager);
    }

    public ControllablePigZombieBase(int id, ControllablePigZombieEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.zombiepig.zpig");
        this.setSound(EntitySound.HURT, "mob.zombiepig.zpighurt");
        this.setSound(EntitySound.DEATH, "mob.zombiepig.zpigdeath");
        this.setSound(EntitySound.STEP, "mob.zombie.step");
        this.setSound(EntitySound.ANGRY, "mob.zombiepig.zpigangry");
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(new BehaviourFloat(this), 0),
                new BehaviourItem(new BehaviourMeleeAttack(this, HumanEntity.class, false, 1.0D), 2),
                new BehaviourItem(new BehaviourMeleeAttack(this, Villager.class, true, 1.0D), 4),
                new BehaviourItem(new BehaviourMoveTowardsRestriction(this, 1.0D), 5),
                new BehaviourItem(new BehaviourMoveThroughVillage(this, false, 1.0D), 6),
                new BehaviourItem(new BehaviourRandomStroll(this, 1.0D), 7),
                new BehaviourItem(new BehaviourLookAtNearestEntity(this, HumanEntity.class, 8.0F), 8),
                new BehaviourItem(new BehaviourLookAtRandom(this), 8)
        };
    }

    @Override
    public BehaviourItem[] getDefaultTargetingBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(new BehaviourHurtByTarget(this, true), 1),
                new BehaviourItem(new BehaviourMoveTowardsNearestAttackableTarget(this, HumanEntity.class, 0, true), 2),
                new BehaviourItem(new BehaviourMoveTowardsNearestAttackableTarget(this, Villager.class, 0, false), 2)
        };
    }
}