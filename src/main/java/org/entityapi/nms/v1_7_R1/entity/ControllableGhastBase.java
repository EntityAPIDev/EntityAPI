package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.Ghast;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;
import org.entityapi.api.entity.ControllableGhast;

public class ControllableGhastBase extends ControllableBaseEntity<Ghast, ControllableGhastEntity> implements ControllableGhast {

    public ControllableGhastBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.GHAST, manager);
    }

    public ControllableGhastBase(int id, ControllableGhastEntity entityHandle, EntityManager manager) {
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