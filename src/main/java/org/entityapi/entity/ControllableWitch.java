package org.entityapi.entity;

import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;
import org.bukkit.entity.Witch;

public class ControllableWitch extends ControllableBaseEntity<Witch> {

    public ControllableWitch(int id, EntityManager manager) {
        super(id, ControllableEntityType.WITCH, manager);
    }

    public ControllableWitch(int id, ControllableWitchEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.witch.idle");
        this.setSound(EntitySound.HURT, "mob.witch.hurt");
        this.setSound(EntitySound.DEATH, "mob.witch.death");
    }
}