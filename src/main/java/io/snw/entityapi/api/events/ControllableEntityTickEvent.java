package io.snw.entityapi.api.events;

import io.snw.entityapi.api.ControllableEntity;
import org.bukkit.event.HandlerList;

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