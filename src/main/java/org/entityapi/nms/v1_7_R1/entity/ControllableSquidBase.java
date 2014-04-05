package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.Squid;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.entity.ControllableSquid;

public class ControllableSquidBase extends ControllableBaseEntity<Squid, ControllableSquidEntity> implements ControllableSquid {

    public ControllableSquidBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.SQUID, manager);
    }

    public ControllableSquidBase(int id, ControllableSquidEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }
}