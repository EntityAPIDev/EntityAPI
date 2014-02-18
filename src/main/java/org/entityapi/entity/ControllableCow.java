package org.entityapi.entity;

import org.bukkit.entity.Cow;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;

public class ControllableCow extends ControllableBaseEntity<Cow, ControllableCowEntity> {

    public ControllableCow(int id, EntityManager manager) {
        super(id, ControllableEntityType.COW, manager);
    }

    public ControllableCow(int id, ControllableCowEntity entityHandle, EntityManager manager) {
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