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

package org.entityapi.nms.v1_7_R1.reflection;

import net.minecraft.server.v1_7_R1.EntityInsentient;
import net.minecraft.server.v1_7_R1.PathfinderGoal;
import org.entityapi.api.internal.Constants;
import org.entityapi.api.reflection.ClassTemplate;
import org.entityapi.api.reflection.FieldAccessor;
import org.entityapi.api.reflection.MethodAccessor;
import org.entityapi.api.reflection.NMSClassTemplate;

import java.util.List;

public class PathfinderGoalSelectorRef {

    public static final ClassTemplate TEMPLATE = NMSClassTemplate.create(Constants.PathfinderGoalSelector.CLASS_NAME.get());

    public static final FieldAccessor<List> LIST_GOALS = TEMPLATE.getField(Constants.PathfinderGoalSelector.LIST_GOALS.get());
    public static final FieldAccessor<List> LIST_ACTIVE_GOALS = TEMPLATE.getField(Constants.PathfinderGoalSelector.LIST_ACTIVE_GOALS.get());

    public static final MethodAccessor<Void> ADD_GOAL = TEMPLATE.getMethod(Constants.PathfinderGoalSelector.METHOD_ADD_GOAL.get(), Integer.class, PathfinderGoal.class);

    private EntityInsentient entityInsentient;

    public PathfinderGoalSelectorRef(EntityInsentient entityInsentient) {
        this.entityInsentient = entityInsentient;
    }

    public List getGoals() {
        return LIST_GOALS.get(this.entityInsentient);
    }

    public List getActiveGoals() {
        return LIST_ACTIVE_GOALS.get(this.entityInsentient);
    }

    public void clearGoals() {
        LIST_GOALS.get(this.entityInsentient).clear();
        LIST_ACTIVE_GOALS.get(this.entityInsentient).clear();
    }

    public void addGoal(PathfinderGoal goal, int priority) {
        ADD_GOAL.invoke(this.entityInsentient, priority, goal);
    }
}