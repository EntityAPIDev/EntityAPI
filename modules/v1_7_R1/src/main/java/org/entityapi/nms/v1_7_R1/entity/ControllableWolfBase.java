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

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Wolf;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.type.ControllableWolf;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals.*;

public class ControllableWolfBase extends ControllableBaseEntity<Wolf, ControllableWolfEntity> implements ControllableWolf {

    public ControllableWolfBase(int id, EntityManager entityManager) {
        super(id, ControllableEntityType.WOLF, entityManager);
    }

    public ControllableWolfBase(int id, ControllableWolfEntity entityHandle, EntityManager entityManager) {
        this(id, entityManager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

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
                new BehaviourItem(new BehaviourFloat(this), 1),
                new BehaviourItem(new BehaviourSit(this), 2),
                new BehaviourItem(new BehaviourLeapAtTarget(this, 0.4F), 3),
                new BehaviourItem(new BehaviourMeleeAttack(this, true, 1.0D), 4),
                new BehaviourItem(new BehaviourFollowTamer(this, 10.0F, 2.0F, 1.0D), 5),
                new BehaviourItem(new BehaviourBreed(this, 1.0D), 6),
                new BehaviourItem(new BehaviourRandomStroll(this, 1.0D), 7),
                new BehaviourItem(new BehaviourBeg(this, new Material[]{Material.BONE}), 8),
                new BehaviourItem(new BehaviourLookAtNearestEntity(this, HumanEntity.class, 8.0F), 9),
                new BehaviourItem(new BehaviourLookAtRandom(this), 9)
        };
    }

    @Override
    public BehaviourItem[] getDefaultTargetingBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(new BehaviourHelpTamerTarget(this), 1),
                new BehaviourItem(new BehaviourDefendTamer(this), 2),
                new BehaviourItem(new BehaviourHurtByTarget(this, true), 3),
                new BehaviourItem(new BehaviourRandomTargetNonTamed(this, Sheep.class, 200, false), 4)
        };
    }
}