package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.Ghast;

public class ControllableGhast extends ControllableAttackingBaseEntity<Ghast> {

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