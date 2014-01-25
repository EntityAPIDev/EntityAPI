package io.snw.entityapi.entity;

import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.Creeper;

public class ControllableCreeper extends ControllableBaseEntity<Creeper> {

    public ControllableCreeper(ControllableCreeperEntity entityHandle) {
        super(ControllableEntityType.CREEPER);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public void explode(int modifier) {
        if (this.handle != null) {
            ((ControllableCreeperEntity) this.handle).explode(modifier);
        }
    }

    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.creeper.say");
        this.setSound(EntitySound.DEATH, "mob.creeper.death");
    }
}