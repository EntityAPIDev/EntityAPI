package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntity;
import io.snw.entityapi.api.ControllableEntityType;
import org.bukkit.entity.EnderDragon;

//TODO: finish this
public class ControllableEnderDragon extends ControllableBaseEntity<EnderDragon> {

    public ControllableEnderDragon(int id, EntityManager entityManager) {
        super(id, ControllableEntityType.ENDERDRAGON, entityManager);
    }

    public ControllableEnderDragon(int id, ControllableEnderDragonEntity entityHandle, EntityManager entityManager) {
        super(id, ControllableEntityType.ENDERDRAGON, entityManager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }
}