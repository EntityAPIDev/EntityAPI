package io.snw.entityapi.api.events;

import io.snw.entityapi.api.ControllableEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;

public abstract class ControllableEntityEvent extends Event {

    private final ControllableEntity controllableEntity;

    public ControllableEntityEvent(final ControllableEntity controllableEntity) {
        this.controllableEntity = controllableEntity;
    }

    /**
     * Returns Entity involved in this event
     *
     * @return Entity involved in this event
     */
    public ControllableEntity getControllableEntity() {
        return this.controllableEntity;
    }

    /**
     * Gets the EntityType of the Entity involved in this event.
     *
     * @return EntityType of the Entity involved in this event
     */
    public EntityType getEntityType() {
        return this.controllableEntity.getBukkitEntity().getType();
    }
}