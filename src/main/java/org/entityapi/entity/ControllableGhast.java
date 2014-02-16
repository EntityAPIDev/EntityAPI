package org.entityapi.entity;

import org.bukkit.entity.Ghast;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;

public class ControllableGhast extends ControllableBaseEntity<Ghast> {

    public ControllableGhast(int id, EntityManager manager) {
        super(id, ControllableEntityType.GHAST, manager);
    }

    public ControllableGhast(int id, ControllableGhastEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.ghast.moan");
        this.setSound(EntitySound.HURT, "mob.ghast.scream");
        this.setSound(EntitySound.DEATH, "mob.ghast.death");
    }
}