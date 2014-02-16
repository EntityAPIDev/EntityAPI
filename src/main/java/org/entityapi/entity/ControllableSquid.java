package org.entityapi.entity;

import org.bukkit.entity.Squid;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;

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