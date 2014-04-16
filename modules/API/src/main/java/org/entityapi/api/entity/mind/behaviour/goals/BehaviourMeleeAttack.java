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

package org.entityapi.api.entity.mind.behaviour.goals;

import org.bukkit.entity.Entity;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.Behaviour;

public class BehaviourMeleeAttack extends Behaviour {

    public BehaviourMeleeAttack(ControllableEntity controllableEntity, boolean ignoreSight) {
        this(controllableEntity, ignoreSight, -1);
    }

    public BehaviourMeleeAttack(ControllableEntity controllableEntity, boolean ignoreSight, double navigationSpeed) {
        this(controllableEntity, null, ignoreSight, navigationSpeed);
    }

    public BehaviourMeleeAttack(ControllableEntity controllableEntity, Class<? extends Entity> typeToAttack, boolean ignoreSight) {
        this(controllableEntity, typeToAttack, ignoreSight, -1);
    }

    public BehaviourMeleeAttack(ControllableEntity controllableEntity, Class<? extends Entity> typeToAttack, boolean ignoreSight, double navigationSpeed) {
        super(controllableEntity, typeToAttack, ignoreSight, navigationSpeed);
    }
}