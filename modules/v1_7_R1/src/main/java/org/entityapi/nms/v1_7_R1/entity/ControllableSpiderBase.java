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
import org.bukkit.entity.Spider;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.entity.type.ControllableSpider;
import org.entityapi.api.entity.mind.behaviour.goals.*;

public class ControllableSpiderBase extends ControllableBaseEntity<Spider, ControllableSpiderEntity> implements ControllableSpider {

    public ControllableSpiderBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.SPIDER, manager);
    }

    public ControllableSpiderBase(int id, ControllableSpiderEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.spider.say");
        this.setSound(EntitySound.HURT, "mob.spider.say");
        this.setSound(EntitySound.DEATH, "mob.spider.death");
        this.setSound(EntitySound.STEP, "mob.spider.step");
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(0, new BehaviourFloat(this)),
                new BehaviourItem(1, new BehaviourLeapAtTarget(this, 1F)),
                new BehaviourItem(2, new BehaviourMoveTowardsRestriction(this)),
                new BehaviourItem(3, new BehaviourRandomStroll(this)),
                new BehaviourItem(4, new BehaviourLookAtNearestEntity(this, HumanEntity.class, 8F)),
                new BehaviourItem(5, new BehaviourLookAtRandom(this)),
        };
    }

    @Override
    public BehaviourItem[] getDefaultTargetingBehaviours() {
        return new BehaviourItem[]{
                new BehaviourItem(0, new BehaviourHurtByTarget(this, true, false, true)),
                new BehaviourItem(1, new BehaviourMoveTowardsNearestAttackableTarget(this, HumanEntity.class, 0, true))
        };
    }
}