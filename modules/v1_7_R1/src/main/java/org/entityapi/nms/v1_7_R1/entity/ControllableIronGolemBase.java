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

import net.minecraft.server.v1_7_R1.IMonster;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.type.ControllableIronGolem;
import org.entityapi.api.entity.type.bukkit.InsentientEntity;
import org.entityapi.api.entity.mind.behaviour.goals.*;

public class ControllableIronGolemBase extends ControllableBaseEntity<IronGolem, ControllableIronGolemEntity> implements ControllableIronGolem {


    public ControllableIronGolemBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.IRON_GOLEM, manager);
    }

    public ControllableIronGolemBase(int id, ControllableIronGolemEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.HURT, "mob.irongolem.hit");
        this.setSound(EntitySound.DEATH, "mob.irongolem.death");
        this.setSound(EntitySound.THROW, "mob.irongolem.throw");
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(1, new BehaviourMeleeAttack(this, true, 1.0D)),
                new BehaviourItem(2, new BehaviourMoveTowardsTarget(this, 32.0F)),
                new BehaviourItem(3, new BehaviourMoveThroughVillage(this, true, 0.6D)),
                new BehaviourItem(4, new BehaviourMoveTowardsRestriction(this, 1.0D)),
                new BehaviourItem(5, new BehaviourOfferFlower(this, LivingEntity.class)),
                new BehaviourItem(6, new BehaviourRandomStroll(this)),
                new BehaviourItem(7, new BehaviourLookAtNearestEntity(this, HumanEntity.class, 6.0F)),
                new BehaviourItem(8, new BehaviourLookAtRandom(this))
        };
    }

    @Override
    public BehaviourItem[] getDefaultTargetingBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(1, new BehaviourDefendVillage(this)),
                new BehaviourItem(2, new BehaviourHurtByTarget(this, false)),
                new BehaviourItem(3, new BehaviourMoveTowardsNearestAttackableTarget(this, InsentientEntity.class, 0, false, true, IMonster.a))
        };
    }
}