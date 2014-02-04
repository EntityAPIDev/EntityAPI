package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.Wither;

public class ControllableWither extends ControllableBaseEntity<Wither> {

    public ControllableWither(int id, EntityManager entityManager) {
        super(id, ControllableEntityType.WITHER, entityManager);
    }

    public ControllableWither(int id, ControllableWitherEntity entityHandle, EntityManager entityManager) {
        super(id, ControllableEntityType.WITHER, entityManager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.wither.idle");
        this.setSound(EntitySound.HURT, "mob.wither.hurt");
        this.setSound(EntitySound.DEATH, "mob.wither.death");
    }
}