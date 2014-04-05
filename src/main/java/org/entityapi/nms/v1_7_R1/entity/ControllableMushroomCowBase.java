package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.MushroomCow;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;
import org.entityapi.api.entity.ControllableMushroomCow;

public class ControllableMushroomCowBase extends ControllableBaseEntity<MushroomCow, ControllableMushroomCowEntity> implements ControllableMushroomCow {

    public ControllableMushroomCowBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.MUSHROOMCOW, manager);
    }

    public ControllableMushroomCowBase(int id, ControllableMushroomCowEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.cow.say");
        this.setSound(EntitySound.HURT, "mob.cow.hurt");
        this.setSound(EntitySound.DEATH, "mob.cow.hurt");
        this.setSound(EntitySound.STEP, "mob.chicken.step");
    }
}