package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.Wolf;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;
import org.entityapi.api.entity.ControllableWolf;

public class ControllableWolfBase extends ControllableBaseEntity<Wolf, ControllableWolfEntity> implements ControllableWolf {

    public ControllableWolfBase(int id, EntityManager entityManager) {
        super(id, ControllableEntityType.WOLF, entityManager);
    }

    public ControllableWolfBase(int id, ControllableWolfEntity entityHandle, EntityManager entityManager) {
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