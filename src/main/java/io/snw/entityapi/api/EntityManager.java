package io.snw.entityapi.api;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class EntityManager implements EntityManagerBase {

    @Override
    public ControllableEntity spawnEntity(EntityType type, Location location) {
        return null;
    }

    @Override
    public ControllableEntity takeControl(Entity entity) {
        return null;
    }

    @Override
    public ControllableEntity spawnNamedEntity(String name, Location location) {
        return null;
    }
}
