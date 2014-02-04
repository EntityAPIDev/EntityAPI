package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import org.bukkit.entity.Squid;

public class ControllableSquid extends ControllableBaseEntity<Squid> {

    public ControllableSquid(int id, EntityManager entityManager) {
        super(id, ControllableEntityType.SQUID, entityManager);
    }

    public ControllableSquid(int id, ControllableSquidEntity entityHandle, EntityManager entityManager) {
        super(id, ControllableEntityType.SQUID, entityManager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }
}