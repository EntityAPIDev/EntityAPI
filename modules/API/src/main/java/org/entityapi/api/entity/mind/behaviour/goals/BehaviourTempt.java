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

package org.entityapi.api.entity.mind.behaviour.goals;

import org.bukkit.Material;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.Behaviour;

public class BehaviourTempt extends Behaviour {

    public BehaviourTempt(ControllableEntity controllableEntity, Material temptItemId) {
        this(controllableEntity, temptItemId, false);
    }

    public BehaviourTempt(ControllableEntity controllableEntity, Material temptItemId, boolean isScaredByMovement) {
        this(controllableEntity, temptItemId, isScaredByMovement, -1);
    }

    public BehaviourTempt(ControllableEntity controllableEntity, Material temptItemId, boolean isScaredByMovement, double navigationSpeed) {
        super(controllableEntity, temptItemId, isScaredByMovement, navigationSpeed);
    }

    @Deprecated
    public BehaviourTempt(ControllableEntity controllableEntity, int temptItemId) {
        this(controllableEntity, temptItemId, false);
    }

    @Deprecated
    public BehaviourTempt(ControllableEntity controllableEntity, int temptItemId, boolean isScaredByMovement) {
        this(controllableEntity, temptItemId, isScaredByMovement, -1);
    }

    @Deprecated
    public BehaviourTempt(ControllableEntity controllableEntity, int temptItemId, boolean isScaredByMovement, double navigationSpeed) {
        super(controllableEntity, temptItemId, isScaredByMovement, navigationSpeed);
    }
}