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
import org.bukkit.entity.Pig;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.type.ControllablePig;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals.*;

public class ControllablePigBase extends ControllableBaseEntity<Pig, ControllablePigEntity> implements ControllablePig {

    public ControllablePigBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.PIG, manager);
    }

    public ControllablePigBase(int id, ControllablePigEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.pig.say");
        this.setSound(EntitySound.HURT, "mob.pig.say");
        this.setSound(EntitySound.DEATH, "mob.pig.death");
        this.setSound(EntitySound.STEP, "mob.pig.step");
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[] {
                new BehaviourItem(new BehaviourFloat(this), 0),
                new BehaviourItem(new BehaviourPanic(this, 1.25D), 1),
                new BehaviourItem(new BehaviourFollowCarrotStick(this, 0.3F), 2),
                new BehaviourItem(new BehaviourBreed(this, 1.0D), 3),
                new BehaviourItem(new BehaviourTempt(this, Material.CARROT, false), 4),
                new BehaviourItem(new BehaviourTempt(this, Material.CARROT_STICK, false), 4),
                new BehaviourItem(new BehaviourFollowParent(this, 1.1D), 5),
                new BehaviourItem(new BehaviourRandomStroll(this, 1.0D), 6),
                new BehaviourItem(new BehaviourLookAtNearestEntity(this, HumanEntity.class, 6.0F), 7),
                new BehaviourItem(new BehaviourLookAtRandom(this), 8)
        };
    }
}