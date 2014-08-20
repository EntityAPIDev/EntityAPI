package org.entityapi.game;

public abstract class IEntityRegistry {

    public abstract void register(EntityRegistrationEntry entityRegistrationEntry);

    public abstract void unregister(EntityRegistrationEntry entityRegistrationEntry);
}
