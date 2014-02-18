package org.entityapi.entity.selector;

import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.IEntitySelector;

public class EntitySelectorLiving implements IEntitySelector {

    @Override
    public boolean a(Entity entity) {
        return entity.isAlive();
    }
}