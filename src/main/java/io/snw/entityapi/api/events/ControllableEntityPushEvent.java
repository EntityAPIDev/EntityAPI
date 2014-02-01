package io.snw.entityapi.api.events;

import io.snw.entityapi.api.ControllableEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;

public class ControllableEntityPushEvent extends ControllableEntityEvent {

    private static final HandlerList handlers = new HandlerList();

    private Vector pushVelocity;

    public ControllableEntityPushEvent(final ControllableEntity controllableEntity, final Vector pushVelocity) {
        super(controllableEntity);
        this.pushVelocity = pushVelocity;
    }

    /**
     * Returns velocity entity was pushed
     *
     * @return Vector representing pushed velocity
     */
    public Vector getPushVelocity() {
        return pushVelocity;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}