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