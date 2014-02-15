package io.snw.entityapi.entity.selector;

import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.IEntitySelector;
import net.minecraft.server.v1_7_R1.IInventory;

public class EntitySelectorContainer implements IEntitySelector {

    @Override
    public boolean a(Entity entity) {
        return entity instanceof IInventory && entity.isAlive();
    }
}