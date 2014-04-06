package org.entityapi.api.events;

import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;
import org.entityapi.api.ControllableEntity;

public class ControllableEntityPushEvent extends ControllableEntityEvent {

    private static final HandlerList handlers = new HandlerList();

    private Vector pushVelocity;

    public ControllableEntityPushEvent(final ControllableEntity controllableEntity, final Vector pushVelocity) {
        super(controllableEntity);
        this.pushVelocity = pushVelocity;
    }

    /**
     * Gets the velocity entity was pushed at
     *
     * @return Vector representing pushed velocity
     */
    public Vector getPushVelocity() {
        return pushVelocity == null ? new Vector(0, 0, 0) : pushVelocity;
    }


    /**
     * Sets the velocity for the entity to be pushed
     *
     * @param pushVelocity new velocity to be pushed. This value cannot be null
     */
    public void setPushVelocity(Vector pushVelocity) {
        if (pushVelocity != null) {
            this.pushVelocity = pushVelocity;
        }
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}