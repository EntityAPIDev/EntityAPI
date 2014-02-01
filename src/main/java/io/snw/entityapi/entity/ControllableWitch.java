package io.snw.entityapi.entity;

import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.Witch;

public class ControllableWitch extends ControllableBaseEntity<Witch> {

    public ControllableWitch(ControllableWitchEntity entityHandle) {
        super(ControllableEntityType.WITCH);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.witch.idle");
        this.setSound(EntitySound.HURT, "mob.witch.hurt");
        this.setSound(EntitySound.DEATH, "mob.witch.death");
    }
}