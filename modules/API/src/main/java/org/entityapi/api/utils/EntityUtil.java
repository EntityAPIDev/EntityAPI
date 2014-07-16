package org.entityapi.api.utils;

import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeField;
import com.captainbern.reflection.SafeMethod;
import com.captainbern.reflection.accessor.FieldAccessor;
import com.captainbern.reflection.accessor.MethodAccessor;

import java.util.List;

import static com.captainbern.reflection.matcher.Matchers.withArguments;
import static com.captainbern.reflection.matcher.Matchers.withType;

public class EntityUtil {

    // TODO: finish this, DSH105 can you take a look at it?

    protected static FieldAccessor<List> GOALS;
    protected static FieldAccessor<List> ACTIVE_GOALS;

    protected static MethodAccessor<Void> ADD_GOAL;

    /**
     * Util classes shouldn't have a public or protected constructor
     */
    private EntityUtil() {}

    public static void addGoal(Object nmsEntityHandle, Object nmsGoalHandle, int priority) {
        ADD_GOAL.invoke(nmsEntityHandle, nmsGoalHandle, priority);
    }

    public static List getGoals(Object nmsEntityHandle) {
        return GOALS.get(nmsEntityHandle);
    }

    public static List getActiveGoals(Object nmsEntityHandle) {
        return ACTIVE_GOALS.get(nmsEntityHandle);
    }

    public static void clearGoals(Object nmsEntityHandle) {
        GOALS.get(nmsEntityHandle).clear();
        ACTIVE_GOALS.get(nmsEntityHandle).clear();
    }

    protected static void initializeFields() {
        try {

            ClassTemplate goalTemplate = new Reflection().reflect(MinecraftReflection.getMinecraftClass("PathfinderGoalSelector"));

            List<SafeMethod<Void>> methodCandidates = goalTemplate.getSafeMethods(withArguments(int.class, goalTemplate.getReflectedClass()));
            if (methodCandidates.size() > 0) {
                ADD_GOAL = methodCandidates.get(0).getAccessor();
            } else {
                throw new RuntimeException("Failed to get the addGoal method!");
            }

            List<SafeField<List>> fieldCandidates = goalTemplate.getSafeFields(withType(List.class));
            if (fieldCandidates.size() > 1) {
                GOALS = fieldCandidates.get(0).getAccessor();
                ACTIVE_GOALS = fieldCandidates.get(0).getAccessor();
            } else {
               throw new RuntimeException("Failed to initialize the goal-lists!");
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize the goal-related fields!");
        }
    }
}
