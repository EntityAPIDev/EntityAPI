package org.entityapi.entity;

import org.bukkit.entity.Pig;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;

public class ControllablePig extends ControllableBaseEntity<Pig> {

    public ControllablePig(int id, EntityManager manager) {
        super(id, ControllableEntityType.PIG, manager);
    }

    public ControllablePig(int id, ControllablePigEntity entityHandle, EntityManager manager) {
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