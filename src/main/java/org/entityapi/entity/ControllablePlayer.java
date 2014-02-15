package org.entityapi.entity;

import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.bukkit.entity.Player;

public class ControllablePlayer extends ControllableBaseEntity<Player> {

    public ControllablePlayer(int id, EntityManager manager) {
        super(id, ControllableEntityType.HUMAN, manager);
    }

    public ControllablePlayer(int id, ControllablePlayerEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }
}
