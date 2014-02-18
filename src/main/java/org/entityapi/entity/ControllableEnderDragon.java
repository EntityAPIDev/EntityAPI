package org.entityapi.entity;

import org.bukkit.entity.EnderDragon;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;

//TODO: finish this
public class ControllableEnderDragon extends ControllableBaseEntity<EnderDragon, ControllableEnderDragonEntity> {

    public ControllableEnderDragon(int id, EntityManager manager) {
        super(id, ControllableEntityType.ENDERDRAGON, manager);
    }

    public ControllableEnderDragon(int id, ControllableEnderDragonEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.enderdragon.growl");
        this.setSound(EntitySound.HIT, "mob.enderdragon.hit");
        this.setSound(EntitySound.DEATH, "mob.enderdragon.end");
        this.setSound(EntitySound.WINGS, "mob.enderdragon.wings");
    }
}