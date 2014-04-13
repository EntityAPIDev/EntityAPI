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
import org.bukkit.entity.MushroomCow;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.type.ControllableMushroomCow;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals.*;

public class ControllableMushroomCowBase extends ControllableBaseEntity<MushroomCow, ControllableMushroomCowEntity> implements ControllableMushroomCow {

    public ControllableMushroomCowBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.MUSHROOMCOW, manager);
    }

    public ControllableMushroomCowBase(int id, ControllableMushroomCowEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.cow.say");
        this.setSound(EntitySound.HURT, "mob.cow.hurt");
        this.setSound(EntitySound.DEATH, "mob.cow.hurt");
        this.setSound(EntitySound.STEP, "mob.cow.step");
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(new BehaviourFloat(this), 0),
                new BehaviourItem(new BehaviourPanic(this, 2.0D), 1),
                new BehaviourItem(new BehaviourBreed(this, 1.0D), 2),
                new BehaviourItem(new BehaviourTempt(this, Material.WHEAT, false, 1.25D), 3),
                new BehaviourItem(new BehaviourFollowParent(this, 1.25D), 4),
                new BehaviourItem(new BehaviourRandomStroll(this, 1.0D), 5),
                new BehaviourItem(new BehaviourLookAtNearestEntity(this, HumanEntity.class, 6.0F), 6),
                new BehaviourItem(new BehaviourLookAtRandom(this), 7)
        };
    }
}