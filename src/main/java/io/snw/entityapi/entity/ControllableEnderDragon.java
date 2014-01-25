package io.snw.entityapi.entity;

import io.snw.entityapi.api.ControllableEntityType;
import org.bukkit.entity.EnderDragon;

//TODO: finish this
public class ControllableEnderDragon extends ControllableBaseEntity<EnderDragon> {

    public ControllableEnderDragon(ControllableEnderDragonEntity entityHandle) {
        super(ControllableEntityType.ENDERDRAGON);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }
}