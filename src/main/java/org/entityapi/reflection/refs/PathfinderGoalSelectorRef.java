package org.entityapi.reflection.refs;

import org.entityapi.internal.Constants;
import org.entityapi.reflection.ClassTemplate;
import org.entityapi.reflection.FieldAccessor;
import org.entityapi.reflection.MethodAccessor;
import org.entityapi.reflection.NMSClassTemplate;
import net.minecraft.server.v1_7_R1.EntityInsentient;
import net.minecraft.server.v1_7_R1.PathfinderGoal;

import java.util.List;

public class PathfinderGoalSelectorRef {

    public static final ClassTemplate TEMPLATE = NMSClassTemplate.create(Constants.PathfinderGoalSelector.CLASS_NAME);

    public static final FieldAccessor<List> LIST_GOALS = TEMPLATE.getField(Constants.PathfinderGoalSelector.LIST_GOALS);
    public static final FieldAccessor<List> LIST_ACTIVE_GOALS = TEMPLATE.getField(Constants.PathfinderGoalSelector.LIST_ACTIVE_GOALS);

    public static final MethodAccessor<Void> ADD_GOAL = TEMPLATE.getMethod(Constants.PathfinderGoalSelector.METHOD_ADD_GOAL, Integer.class, PathfinderGoal.class);

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