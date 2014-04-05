package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.Enderman;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntityManager;
import org.entityapi.api.EntitySound;
import org.entityapi.api.entity.ControllableEnderman;

public class ControllableEndermanBase extends ControllableBaseEntity<Enderman, ControllableEndermanEntity> implements ControllableEnderman {

    public ControllableEndermanBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.ENDERMAN, manager);
    }

    public ControllableEndermanBase(int id, ControllableEndermanEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "idle", "mob.enderman.idle");
        this.setSound(EntitySound.IDLE, "scream", "mob.enderman.scream");
        this.setSound(EntitySound.HURT, "mob.enderman.hit");
        this.setSound(EntitySound.DEATH, "mob.enderman.death");
        this.setSound(EntitySound.STARE, "mob.enderman.stare");
        this.setSound(EntitySound.TELEPORT, "mob.enderman.portal");
    }
}