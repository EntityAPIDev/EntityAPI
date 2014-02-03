package io.snw.entityapi.utils;

import io.snw.entityapi.entity.ControllablePlayerEntity;
import net.minecraft.server.v1_7_R1.EntityInsentient;
import net.minecraft.server.v1_7_R1.EntityLiving;
import net.minecraft.server.v1_7_R1.Navigation;

public class EntityUtil {

    /**
     * Pure NMS-Methods
     * @param entityLiving
     * @return Navigation
     */
    public static Navigation getNavigation(EntityLiving entityLiving) {
        if(entityLiving instanceof EntityInsentient) {
            return ((EntityInsentient) entityLiving).getNavigation();
        } else if(entityLiving instanceof ControllablePlayerEntity) {
            return ((ControllablePlayerEntity) entityLiving).getNavigation();
        }
        return null;
    }
}
