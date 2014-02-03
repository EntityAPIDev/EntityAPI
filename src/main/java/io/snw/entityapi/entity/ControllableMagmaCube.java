package io.snw.entityapi.entity;

import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.MagmaCube;

public class ControllableMagmaCube extends ControllableAttackingBaseEntity<MagmaCube> {

    public ControllableMagmaCube(ControllableMagmaCubeEntity entityHandle) {
        super(ControllableEntityType.LAVA_SLIME);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        for (EntitySound type : new EntitySound[] {EntitySound.IDLE, EntitySound.HURT, EntitySound.DEATH}) {
            this.setSound(type, "mob." + (type == EntitySound.IDLE ? "magmacube" : "slime") + ".big", "big");
            this.setSound(type, "mob." + (type == EntitySound.IDLE ? "magmacube" : "slime") + ".small", "small");
        }
        this.setSound(EntitySound.ATTACK, "mob.attack");
    }
}