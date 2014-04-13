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
import org.entityapi.api.entity.type.ControllableOcelot;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals.*;

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
                new BehaviourItem(new BehaviourFloat(this), 1),
                new BehaviourItem(new BehaviourSit(this), 2),
                new BehaviourItem(new BehaviourTempt(this, Material.RAW_FISH, true, 0.6D), 3),
                new BehaviourItem(new BehaviourAvoidEntity(this, HumanEntity.class, 16.0F, 0.8D, 1.33D), 4),
                new BehaviourItem(new BehaviourFollowTamer(this, 10.0F, 5.0F), 5),
                new BehaviourItem(new BehaviourSitOnBlock(this, 1.33D), 6),
                new BehaviourItem(new BehaviourLeapAtTarget(this, 0.3F), 7),
                new BehaviourItem(new BehaviourOcelotAttack(this), 8),
                new BehaviourItem(new BehaviourBreed(this, 0.8D), 9),
                new BehaviourItem(new BehaviourRandomStroll(this, 0.8D), 10),
                new BehaviourItem(new BehaviourLookAtNearestEntity(this, HumanEntity.class, 10.0F), 11),
        };
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(new BehaviourRandomTargetNonTamed(this, Chicken.class, 750, false), 1)
        };
    }
}