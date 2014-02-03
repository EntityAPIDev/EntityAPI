package io.snw.entityapi.entity;

import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.Wolf;

public class ControllableWolf extends ControllableBaseEntity<Wolf> {

    public ControllableWolf(ControllableWolfEntity entityHandle) {
        super(ControllableEntityType.WOLF);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
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