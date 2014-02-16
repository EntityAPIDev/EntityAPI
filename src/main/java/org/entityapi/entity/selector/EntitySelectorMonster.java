package org.entityapi.entity.selector;

import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.IEntitySelector;
import net.minecraft.server.v1_7_R1.IMonster;

public class EntitySelectorMonster implements IEntitySelector {

    @Override
    public boolean a(Entity entity) {
        return entity instanceof IMonster;
    }
}