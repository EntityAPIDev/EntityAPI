package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.Cow;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntityManager;
import org.entityapi.api.EntitySound;
import org.entityapi.api.entity.ControllableCow;

public class ControllableCowBase extends ControllableBaseEntity<Cow, ControllableCowEntity> implements ControllableCow {

    public ControllableCowBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.COW, manager);
    }

    public ControllableCowBase(int id, ControllableCowEntity entityHandle, EntityManager manager) {
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