package org.entityapi.entity.selector;

import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.EntityLiving;
import net.minecraft.server.v1_7_R1.EnumMonsterType;
import net.minecraft.server.v1_7_R1.IEntitySelector;

public class EntitySelectorNotUndead implements IEntitySelector {

    @Override
    public boolean a(Entity entity) {
        return entity instanceof EntityLiving && ((EntityLiving) entity).getMonsterType() != EnumMonsterType.UNDEAD;
    }
}
