package org.entityapi.entity.selector;

import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.IMonster;
import net.minecraft.server.v1_7_R1.IEntitySelector;

public class EntitySelectorMonster implements IEntitySelector {

    @Override
    public boolean a(Entity entity) {
        return entity instanceof IMonster;
    }
}