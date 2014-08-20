package org.entityapi.game;

import org.entityapi.api.entity.ControllableEntityType;

public abstract class IEntityRegistry {

    public abstract void register(EntityRegistrationEntry entityRegistrationEntry);

    public abstract void unregister(EntityRegistrationEntry entityRegistrationEntry);

    public abstract EntityRegistrationEntry createRegistrationEntryFor(ControllableEntityType entityType);
}
