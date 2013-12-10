package io.snw.entityapi.entity.types;

import io.snw.entityapi.entity.ControllableEntity;
import net.minecraft.server.v1_7_R1.World;

public class EntityCow extends net.minecraft.server.v1_7_R1.EntityCow implements ControllableEntity{

    public EntityCow(World world) {
        super(world);
    }
}
