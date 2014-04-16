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

import org.entityapi.api.ProjectileType;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.Behaviour;

public class BehaviourRangedAttack extends Behaviour {

    public BehaviourRangedAttack(ControllableEntity controllableEntity, int minDelay, float range) {
        this(controllableEntity, ProjectileType.DEFAULT, minDelay, range);
    }

    public BehaviourRangedAttack(ControllableEntity controllableEntity, int minDelay, int maxDelay, float range) {
        this(controllableEntity, ProjectileType.DEFAULT, minDelay, maxDelay, range);
    }

    public BehaviourRangedAttack(ControllableEntity controllableEntity, ProjectileType projectileType, int minDelay, float range) {
        this(controllableEntity, projectileType, minDelay, minDelay, range);
    }

    public BehaviourRangedAttack(ControllableEntity controllableEntity, ProjectileType projectileType, int minDelay, int maxDelay, float range) {
        this(controllableEntity, minDelay, maxDelay, range, -1);
    }

    public BehaviourRangedAttack(ControllableEntity controllableEntity, int minDelay, float range, double navigationSpeed) {
        this(controllableEntity, ProjectileType.DEFAULT, minDelay, range, navigationSpeed);
    }

    public BehaviourRangedAttack(ControllableEntity controllableEntity, int minDelay, int maxDelay, float range, double navigationSpeed) {
        this(controllableEntity, ProjectileType.DEFAULT, minDelay, maxDelay, range, navigationSpeed);
    }

    public BehaviourRangedAttack(ControllableEntity controllableEntity, ProjectileType projectileType, int minDelay, float range, double navigationSpeed) {
        this(controllableEntity, projectileType, minDelay, minDelay, range, navigationSpeed);
    }

    public BehaviourRangedAttack(ControllableEntity controllableEntity, ProjectileType projectileType, int minDelay, int maxDelay, float range, double navigationSpeed) {
        super(controllableEntity, projectileType, minDelay, maxDelay, range, navigationSpeed);
    }
}