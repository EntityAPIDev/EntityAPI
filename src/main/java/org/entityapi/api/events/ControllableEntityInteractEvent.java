package org.entityapi.api.events;

import org.entityapi.api.ControllableEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class ControllableEntityInteractEvent extends ControllableEntityEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    private Player who;
    private Action action;

    public ControllableEntityInteractEvent(final ControllableEntity controllableEntity, final Player who, final Action action) {
        super(controllableEntity);
        this.who = who;
        this.action = action;
    }

    /**
     * Returns the action type
     *
     * @return Action returns the type of interaction
     */
    public Action getAction() {
        return action;
    }

    /**
     * Returns the player involved in this event
     *
     * @return Player who is involved in this event
     */
    public final Player getPlayer() {
        return who;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}