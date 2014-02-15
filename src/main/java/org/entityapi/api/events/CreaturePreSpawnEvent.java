package org.entityapi.api.events;

import net.minecraft.server.v1_7_R1.EntityLiving;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class CreaturePreSpawnEvent extends Event implements Cancellable {
    public static final HandlerList handlers = new HandlerList();
    public boolean cancelled;
    public EntityType entityType;
    public int minSpawnCount, maxSpawnCount;
    public Location spawnLocation = new Location(null, 0, 0, 0);
    private Class<? extends EntityLiving> customEntityClass;

    public CreaturePreSpawnEvent() {
    }

    public CreaturePreSpawnEvent(EntityType type, int minSpawnCount, int maxSpawnCount, Location location) {
        this.entityType = type;
        this.minSpawnCount = minSpawnCount;
        this.maxSpawnCount = maxSpawnCount;
        this.spawnLocation = location;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public EntityType getEntityType() {
        return this.entityType;
    }

    public int getMinSpawnCount() {
        return this.minSpawnCount;
    }

    public void setMinSpawnCount(int minSpawnCount) {
        this.minSpawnCount = minSpawnCount;
    }

    public int getMaxSpawnCount() {
        return this.maxSpawnCount;
    }

    public void setMaxSpawnCount(int maxSpawnCount) {
        this.maxSpawnCount = maxSpawnCount;
    }

    public Location getSpawnLocation() {
        return this.spawnLocation;
    }

    public Class<? extends EntityLiving> getCustomEntityClass() {
        return this.customEntityClass;
    }

    public void setCustomEntityClass(Class<? extends EntityLiving> customEntityClass) {
        this.customEntityClass = customEntityClass;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
