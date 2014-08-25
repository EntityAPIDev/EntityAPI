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

package org.entityapi.nms.v1_7_R1.entity.mind.behaviour;

import net.minecraft.server.v1_7_R1.EntityLiving;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourGoal;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;

public abstract class BehaviourGoalBase<T extends ControllableEntity, S extends EntityLiving> implements BehaviourGoal<T> {

    private T controllableEntity;

    public BehaviourGoalBase(T controllableEntity) {
        this.controllableEntity = controllableEntity;
    }

    @Override
    public T getControllableEntity() {
        return controllableEntity;
    }

    public S getHandle() {
        return (S) this.getControllableEntity().getHandle();
    }

    @Override
    public abstract BehaviourType getType();

    @Override
    public abstract String getDefaultKey();

    @Override
    public abstract boolean shouldStart(); //a

    @Override
    public boolean shouldContinue() { //b
        return shouldStart();
    }

    @Override
    public void start() { //c
    }

    @Override
    public void finish() { //d
    }

    @Override
    public boolean isContinuous() {
        return true;
    }

    @Override
    public void tick() {
    } //e
}