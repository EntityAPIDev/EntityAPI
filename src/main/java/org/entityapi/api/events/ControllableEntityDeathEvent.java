package org.entityapi.api.events;

import org.entityapi.api.ControllableEntity;
import org.bukkit.event.HandlerList;

/**
 * Called when a ControllableEntity dies
 */
public class ControllableEntityDeathEvent extends ControllableEntityEvent {

    private static final HandlerList handlers = new HandlerList();

    public ControllableEntityDeathEvent(final ControllableEntity controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}