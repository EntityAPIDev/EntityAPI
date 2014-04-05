package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.Wither;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntityManager;
import org.entityapi.api.EntitySound;
import org.entityapi.api.entity.ControllableWither;

public class ControllableWitherBase extends ControllableBaseEntity<Wither, ControllableWitherEntity> implements ControllableWither {

    public ControllableWitherBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.WITHER, manager);
    }

    public ControllableWitherBase(int id, ControllableWitherEntity entityHandle, EntityManager manager) {
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