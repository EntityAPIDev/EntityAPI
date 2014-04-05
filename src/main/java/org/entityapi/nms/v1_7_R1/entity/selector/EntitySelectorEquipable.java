package org.entityapi.nms.v1_7_R1.entity.selector;

import net.minecraft.server.v1_7_R1.*;

public class EntitySelectorEquipable implements IEntitySelector {

    private final ItemStack c;

    public EntitySelectorEquipable(ItemStack itemstack) {
        this.c = itemstack;
    }

    @Override
    public boolean a(Entity entity) {
        if (!entity.isAlive()) {
            return false;
        } else if (!(entity instanceof EntityLiving)) {
            return false;
        } else {
            EntityLiving entityliving = (EntityLiving) entity;

            return entityliving.getEquipment(EntityInsentient.b(this.c)) != null ? false : (entityliving instanceof EntityInsentient ? ((EntityInsentient) entityliving).bH() : entityliving instanceof EntityHuman);
        }
    }
}