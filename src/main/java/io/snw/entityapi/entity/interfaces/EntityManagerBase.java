package io.snw.entityapi.entity.interfaces;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public interface EntityManagerBase {

    ControllableEntity spawnEntity(EntityType type, Location location);

    ControllableEntity takeControl(Entity entity);

    ControllableEntity spawnNamedEntity(String name, Location location);

}
