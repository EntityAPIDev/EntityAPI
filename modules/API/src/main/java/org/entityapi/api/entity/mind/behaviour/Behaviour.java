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

package org.entityapi.api.entity.mind.behaviour;

import org.entityapi.api.reflection.APIReflection;
import org.entityapi.api.reflection.SafeConstructor;

public abstract class Behaviour {

    private BehaviourGoal behaviourGoal;

    protected Behaviour(Object... args) {
        Class<?>[] classes = new Class[args.length];
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                classes[i] = args[i].getClass();
            }
        }
        this.behaviourGoal = new SafeConstructor<BehaviourGoal>(APIReflection.getVersionedClass("entity.mind.behaviour.goals.BehaviourGoal" + this.getKey()), classes).newInstance(args);
    }

    public Behaviour(BehaviourGoal behaviourGoal) {
        this.behaviourGoal = behaviourGoal;
    }

    public BehaviourGoal getGoal() {
        return this.behaviourGoal;
    }

    protected String getKey() {
        return this.getClass().getSimpleName().split("Goal")[1];
    }
}