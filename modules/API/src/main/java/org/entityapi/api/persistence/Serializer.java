package org.entityapi.api.persistence;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.entityapi.api.entity.ControllableEntityHandle;
import org.entityapi.api.entity.impl.ControllableBaseEntity;

import java.io.DataOutput;

public interface Serializer {

    public <T extends LivingEntity, S extends ControllableEntityHandle<T>> void serialize(ControllableBaseEntity<T, S> entity, DataOutput output);
}
