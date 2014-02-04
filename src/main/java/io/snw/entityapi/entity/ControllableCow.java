package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.Cow;

public class ControllableCow extends ControllableBaseEntity<Cow> {

    public ControllableCow(int id, EntityManager entityManager) {
        super(id, ControllableEntityType.COW, entityManager);
    }

    public ControllableCow(int id, ControllableCowEntity entityHandle, EntityManager entityManager) {
        super(id, ControllableEntityType.COW, entityManager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.cow.say");
        this.setSound(EntitySound.HURT, "mob.cow.hurt");
        this.setSound(EntitySound.DEATH, "mob.cow.hurt");
        this.setSound(EntitySound.STEP, "mob.chicken.step");
    }
}