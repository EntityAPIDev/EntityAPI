package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.Player;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.entity.ControllablePlayer;

public class ControllablePlayerBase extends ControllableBaseEntity<Player, ControllablePlayerEntity> implements ControllablePlayer {

    public ControllablePlayerBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.HUMAN, manager);
    }

    public ControllablePlayerBase(int id, ControllablePlayerEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }
}
