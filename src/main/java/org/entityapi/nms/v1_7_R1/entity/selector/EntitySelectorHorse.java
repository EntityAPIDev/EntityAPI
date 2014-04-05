package org.entityapi.nms.v1_7_R1.entity.selector;

import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.EntityHorse;
import net.minecraft.server.v1_7_R1.IEntitySelector;

public class EntitySelectorHorse implements IEntitySelector {

    @Override
    public boolean a(Entity entity) {
        return entity instanceof EntityHorse && ((EntityHorse) entity).cm();
    }
}