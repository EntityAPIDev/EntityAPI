package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.Pig;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntityManager;
import org.entityapi.api.EntitySound;
import org.entityapi.api.entity.ControllablePig;

public class ControllablePigBase extends ControllableBaseEntity<Pig, ControllablePigEntity> implements ControllablePig {

    public ControllablePigBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.PIG, manager);
    }

    public ControllablePigBase(int id, ControllablePigEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.pig.say");
        this.setSound(EntitySound.HURT, "mob.pig.say");
        this.setSound(EntitySound.DEATH, "mob.pig.death");
        this.setSound(EntitySound.STEP, "mob.pig.step");
    }
}