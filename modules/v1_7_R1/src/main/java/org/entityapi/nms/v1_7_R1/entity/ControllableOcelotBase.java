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
import org.bukkit.entity.Chicken;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Ocelot;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.mind.behaviour.goals.*;
import org.entityapi.api.entity.type.ControllableOcelot;

public class ControllableOcelotBase extends ControllableBaseEntity<Ocelot, ControllableOcelotEntity> implements ControllableOcelot {

    public ControllableOcelotBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.OZELOT, manager);
    }

    public ControllableOcelotBase(int id, ControllableOcelotEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.cat.purr", "purr");
        this.setSound(EntitySound.IDLE, "mob.cat.purreow", "purreow");
        this.setSound(EntitySound.IDLE, "mob.cat.meow", "meow");
        this.setSound(EntitySound.HURT, "mob.cat.hitt");
        this.setSound(EntitySound.DEATH, "mob.cat.hitt");
    }

    @Override
    public BehaviourItem[] getDefaultTargetingBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(1, new BehaviourFloat(this)),
                new BehaviourItem(2, new BehaviourSit(this)),
                new BehaviourItem(3, new BehaviourTempt(this, Material.RAW_FISH, true, 0.6D)),
                new BehaviourItem(4, new BehaviourAvoidEntity(this, HumanEntity.class, 16.0F, 0.8D, 1.33D)),
                new BehaviourItem(5, new BehaviourFollowTamer(this, 10.0F, 5.0F)),
                new BehaviourItem(6, new BehaviourSitOnBlock(this, 1.33D)),
                new BehaviourItem(7, new BehaviourLeapAtTarget(this, 0.3F)),
                new BehaviourItem(8, new BehaviourOcelotAttack(this)),
                new BehaviourItem(9, new BehaviourBreed(this, 0.8D)),
                new BehaviourItem(10, new BehaviourRandomStroll(this, 0.8D)),
                new BehaviourItem(11, new BehaviourLookAtNearestEntity(this, HumanEntity.class, 10.0F)),
        };
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(1, new BehaviourRandomTargetNonTamed(this, Chicken.class, 750, false))
        };
    }
}