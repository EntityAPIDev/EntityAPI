package org.entityapi.entity;

import org.bukkit.entity.Chicken;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;

public class ControllableChicken extends ControllableBaseEntity<Chicken> {

    public ControllableChicken(int id, EntityManager manager) {
        super(id, ControllableEntityType.CHICKEN, manager);
    }

    public ControllableChicken(int id, ControllableChickenEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.chicken.idle");
        this.setSound(EntitySound.HURT, "mob.chicken.hurt");
        this.setSound(EntitySound.DEATH, "mob.chicken.hurt");
        this.setSound(EntitySound.STEP, "mob.chicken.step");
        this.setSound(EntitySound.LAY_EGG, "mob.chicken.plop");
    }
}