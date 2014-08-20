package org.entityapi.game;

import org.bukkit.entity.EntityType;

public abstract class IEntityRegistry {

    public abstract void register(EntityRegistrationEntry entityRegistrationEntry);

    public abstract void unregister(EntityRegistrationEntry entityRegistrationEntry);

    public abstract EntityRegistrationEntry createRegistrationEntryFor(EntityType entityType);
}
