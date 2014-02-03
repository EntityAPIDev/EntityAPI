package io.snw.entityapi.entity;

import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.Pig;

public class ControllablePig extends ControllableBaseEntity<Pig> {

    public ControllablePig(ControllablePigEntity entityHandle) {
        super(ControllableEntityType.PIG);
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