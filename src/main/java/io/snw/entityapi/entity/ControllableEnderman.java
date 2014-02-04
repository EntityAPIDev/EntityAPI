package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.Enderman;

public class ControllableEnderman extends ControllableAttackingBaseEntity<Enderman> {

    public ControllableEnderman(int id, EntityManager entityManager) {
        super(id, ControllableEntityType.ENDERMAN, entityManager);
    }

    public ControllableEnderman(int id, ControllableEndermanEntity entityHandle, EntityManager entityManager) {
        super(id, ControllableEntityType.ENDERMAN, entityManager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public void initSounds() {
        this.setSound(EntitySound.IDLE, "idle", "mob.enderman.idle");
        this.setSound(EntitySound.IDLE, "scream", "mob.enderman.scream");
        this.setSound(EntitySound.HURT, "mob.enderman.hit");
        this.setSound(EntitySound.DEATH, "mob.enderman.death");
        this.setSound(EntitySound.STARE, "mob.enderman.stare");
        this.setSound(EntitySound.TELEPORT, "mob.enderman.portal");
    }
}