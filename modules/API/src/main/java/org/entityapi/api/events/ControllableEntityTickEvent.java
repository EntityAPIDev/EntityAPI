package org.entityapi.api.events;

import org.bukkit.event.HandlerList;
import org.entityapi.api.ControllableEntity;

public class ControllableEntityTickEvent extends ControllableEntityEvent {

    private static final HandlerList handlers = new HandlerList();

    public ControllableEntityTickEvent(final ControllableEntity controllableEntity) {
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