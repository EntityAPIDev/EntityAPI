package org.entityapi.api.events;

import net.minecraft.server.v1_7_R1.EntityAnimal;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.entityapi.api.ControllableEntity;

public class ControllableEntityBreedEvent extends ControllableEntityEvent {

    private static final HandlerList handlers = new HandlerList();

    private Animals child;
    private Animals mate;
    private Player breeder;

    public ControllableEntityBreedEvent(ControllableEntity<Animals, EntityAnimal> firstParent, Animals mate, Animals child, Player breeder) {
        super(firstParent);
        this.child = child;
        this.mate = mate;
        this.breeder = breeder;
    }

    public Animals getChild() {
        return child;
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
}