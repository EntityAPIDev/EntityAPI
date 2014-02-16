package org.entityapi.entity;

import org.bukkit.entity.Wither;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;

public class ControllableWither extends ControllableBaseEntity<Wither> {

    public ControllableWither(int id, EntityManager manager) {
        super(id, ControllableEntityType.WITHER, manager);
    }

    public ControllableWither(int id, ControllableWitherEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.wither.idle");
        this.setSound(EntitySound.HURT, "mob.wither.hurt");
        this.setSound(EntitySound.DEATH, "mob.wither.death");
    }
}