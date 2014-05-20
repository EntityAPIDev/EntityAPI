/*
 * Copyright (C) EntityAPI Team
 *
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

package org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.EntityAnimal;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.BasicEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourGoalBase;

import java.util.Iterator;
import java.util.List;

public class BehaviourGoalFollowParent extends BehaviourGoalBase {

    private EntityAnimal parent;
    private int followTicks;
    private double navigationSpeed;

    public BehaviourGoalFollowParent(ControllableEntity<? extends Animals> controllableEntity, double navigationSpeed) {
        super(controllableEntity);
        this.navigationSpeed = navigationSpeed;
    }

    @Override
    public ControllableEntity<? extends Animals> getControllableEntity() {
        return super.getControllableEntity();
    }

    @Override
    public EntityAnimal getHandle() {
        return (EntityAnimal) BasicEntityUtil.getInstance().getHandle(this.getControllableEntity());
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.SUBCONSCIOUS;
    }

    @Override
    public String getDefaultKey() {
        return "Follow Parent";
    }

    @Override
    public boolean shouldStart() {
        if (this.getHandle().getAge() >= 0) {
            return false;
        } else {
            List nearbyAnimals = this.getHandle().world.a(this.getHandle().getClass(), this.getHandle().boundingBox.grow(8.0D, 4.0D, 8.0D));
            EntityAnimal nearest = null;
            double minDistance = Double.MAX_VALUE;
            Iterator iterator = nearbyAnimals.iterator();

            while (iterator.hasNext()) {
                EntityAnimal animal = (EntityAnimal) iterator.next();

                if (animal.getAge() >= 0) {
                    double dist = this.getHandle().e(animal);

                    if (dist <= minDistance) {
                        minDistance = dist;
                        nearest = animal;
                    }
                }
            }

            if (nearest == null) {
                return false;
            } else if (minDistance < 9.0D) {
                return false;
            } else {
                this.parent = nearest;
                return true;
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        if (!this.parent.isAlive()) {
            return false;
        } else {
            double dist = this.getHandle().e(this.parent);

            return dist >= 9.0D && dist <= 256.0D;
        }
    }

    @Override
    public void start() {
        this.followTicks = 0;
    }

    @Override
    public void finish() {
        this.parent = null;
    }

    @Override
    public void tick() {
        if (--this.followTicks <= 0) {
            this.followTicks = 10;
            this.getControllableEntity().navigateTo((LivingEntity) this.parent.getBukkitEntity(), this.navigationSpeed > 0 ? this.navigationSpeed : this.getControllableEntity().getSpeed());
        }
    }
}