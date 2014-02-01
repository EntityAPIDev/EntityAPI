package io.snw.entityapi.entity;

import io.snw.entityapi.api.ControllableEntityType;
import org.bukkit.entity.Player;

public class ControllablePlayer extends ControllableAttackingBaseEntity<Player> {

    public ControllablePlayer(ControllableEntityType entityType) {
        super(entityType);
    }
}
