package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.Sound;
import org.bukkit.entity.EnderDragon;

//TODO: finish this
public class ControllableEnderDragon extends ControllableBaseEntity<EnderDragon> {
    
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