package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import org.bukkit.entity.Player;

public class ControllablePlayer extends ControllableAttackingBaseEntity<Player> {

    public ControllablePlayer(int id, EntityManager entityManager) {
        super(id, ControllableEntityType.HUMAN, entityManager);
    }

    public ControllablePlayer(int id, ControllablePlayerEntity entityType, EntityManager entityManager) {
        super(id, ControllableEntityType.HUMAN, entityManager);
    }
}
