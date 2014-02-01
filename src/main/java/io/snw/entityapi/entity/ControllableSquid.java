package io.snw.entityapi.entity;

import io.snw.entityapi.api.ControllableEntityType;
import org.bukkit.entity.Squid;

public class ControllableSquid extends ControllableBaseEntity<Squid> {

    public ControllableSquid(ControllableSquidEntity entityHandle) {
        super(ControllableEntityType.SQUID);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }
}