package org.entityapi.game;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.ControllableEntityHandle;

public abstract class IEntitySpawnHandler {

    public abstract <T extends ControllableEntityHandle<? extends LivingEntity>> T createHandle(ControllableEntity entity, Location location);
}
