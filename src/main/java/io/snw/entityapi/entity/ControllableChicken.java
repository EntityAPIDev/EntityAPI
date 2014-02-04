package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.Chicken;

public class ControllableChicken extends ControllableBaseEntity<Chicken> {

    public ControllableChicken(int id, EntityManager entityManager) {
        super(id, ControllableEntityType.CHICKEN, entityManager);
    }

    public ControllableChicken(int id, ControllableChickenEntity entityHandle, EntityManager entityManager) {
        super(id, ControllableEntityType.CHICKEN, entityManager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.chicken.idle");
        this.setSound(EntitySound.HURT, "mob.chicken.hurt");
        this.setSound(EntitySound.DEATH, "mob.chicken.hurt");
        this.setSound(EntitySound.STEP, "mob.chicken.step");
        this.setSound(EntitySound.LAY_EGG, "mob.chicken.plop");
    }
}