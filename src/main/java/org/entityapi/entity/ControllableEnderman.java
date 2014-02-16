package org.entityapi.entity;

import org.bukkit.entity.Enderman;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;

public class ControllableEnderman extends ControllableBaseEntity<Enderman> {

    public ControllableEnderman(int id, EntityManager manager) {
        super(id, ControllableEntityType.ENDERMAN, manager);
    }

    public ControllableEnderman(int id, ControllableEndermanEntity entityHandle, EntityManager manager) {
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