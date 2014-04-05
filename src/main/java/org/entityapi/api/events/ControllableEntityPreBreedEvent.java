package org.entityapi.api.events;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.entityapi.api.ControllableEntity;

public class ControllableEntityPreBreedEvent extends ControllableEntityEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    private Animals mate;
    private Player breeder;

    public ControllableEntityPreBreedEvent(ControllableEntity<Animals> firstParent, Animals mate, Player breeder) {
        super(firstParent);
        this.mate = mate;
        this.breeder = breeder;
    }

    public Player getBreeder() {
        return breeder;
    }

    public Animals getMate() {
        return mate;
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