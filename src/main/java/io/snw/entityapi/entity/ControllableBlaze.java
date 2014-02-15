package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.Blaze;

public class ControllableBlaze extends ControllableBaseEntity<Blaze> {

    public ControllableBlaze(int id, EntityManager manager) {
        super(id, ControllableEntityType.BLAZE, manager);
    }

    public ControllableBlaze(int id, ControllableBlazeEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.blaze.breathe");
        this.setSound(EntitySound.HURT, "mob.blaze.hit");
        this.setSound(EntitySound.DEATH, "mob.blaze.death");
    }
}