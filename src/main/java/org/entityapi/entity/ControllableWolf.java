package org.entityapi.entity;

import org.bukkit.entity.Wolf;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;

public class ControllableWolf extends ControllableBaseEntity<Wolf, ControllableWolfEntity> {

    public ControllableWolf(int id, EntityManager entityManager) {
        super(id, ControllableEntityType.WOLF, entityManager);
    }

    public ControllableWolf(int id, ControllableWolfEntity entityHandle, EntityManager entityManager) {
        this(id, entityManager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public void initSounds() {
        this.setSound(EntitySound.IDLE, "growl", "mob.wolf.growl");
        this.setSound(EntitySound.IDLE, "whine", "mob.wolf.whine");
        this.setSound(EntitySound.IDLE, "panting", "mob.wolf.panting");
        this.setSound(EntitySound.IDLE, "bark", "mob.wolf.bark");
        this.setSound(EntitySound.HURT, "mob.wolf.hurt");
        this.setSound(EntitySound.DEATH, "mob.wolf.death");
        this.setSound(EntitySound.STEP, "mob.wolf.step");
        this.setSound(EntitySound.SHAKE, "mob.wolf.shake");
    }
}