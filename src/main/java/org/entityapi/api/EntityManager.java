package org.entityapi.api;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

public interface EntityManager {

    public Plugin getOwningPlugin();

    public boolean isKeepEntitiesInMemory();

    public void setKeepEntitiesInMemory(boolean bool);

    public ControllableEntity spawnEntity(ControllableEntityType entityType, Location location);

    public ControllableEntity spawnEntity(ControllableEntityType entityType, Location location, boolean prepare);

    public Collection<ControllableEntity> getEntities();

    public void despawnAll();

    public void despawnAll(DespawnReason despawnReason);

    @Override
    public String toString();

    @Override
    public int hashCode();
}
