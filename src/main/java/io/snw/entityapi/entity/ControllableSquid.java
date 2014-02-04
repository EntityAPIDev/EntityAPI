package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import org.bukkit.entity.Squid;

public class ControllableSquid extends ControllableBaseEntity<Squid> {

    public ControllableSquid(int id, EntityManager manager) {
        super(id, ControllableEntityType.SQUID, manager);
    }

    public ControllableSquid(int id, ControllableSquidEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }
}