package io.snw.entityapi.api.events;

import io.snw.entityapi.api.ControllableEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class ControllableEntityCollideEvent extends ControllableEntityEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    private Entity collidedWith;

    public ControllableEntityCollideEvent(final ControllableEntity controllableEntity, final Entity collidedWith) {
        super(controllableEntity);
        this.collidedWith = collidedWith;
    }

    /**
     * Returns Entity collided with
     *
     * @return Entity collided with
     */
    public Entity getCollidedWith() {
        return collidedWith;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}