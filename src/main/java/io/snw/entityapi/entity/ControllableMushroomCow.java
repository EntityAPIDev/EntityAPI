package io.snw.entityapi.entity;

import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.MushroomCow;

public class ControllableMushroomCow extends ControllableBaseEntity<MushroomCow> {

    public ControllableMushroomCow(ControllableMushroomCowEntity entityHandle) {
        super(ControllableEntityType.MUSHROOMCOW);
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