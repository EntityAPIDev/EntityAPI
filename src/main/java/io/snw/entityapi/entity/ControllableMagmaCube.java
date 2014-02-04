package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.MagmaCube;

public class ControllableMagmaCube extends ControllableAttackingBaseEntity<MagmaCube> {

    public ControllableMagmaCube(int id, EntityManager entityManager) {
        super(id, ControllableEntityType.LAVA_SLIME, entityManager);
    }

    public ControllableMagmaCube(int id, ControllableMagmaCubeEntity entityHandle, EntityManager entityManager) {
        super(id, ControllableEntityType.LAVA_SLIME, entityManager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public void initSounds() {
        for (EntitySound type : new EntitySound[] {EntitySound.IDLE, EntitySound.HURT, EntitySound.DEATH}) {
            this.setSound(type, "mob." + (type == EntitySound.IDLE ? "magmacube" : "slime") + ".big", "big");
            this.setSound(type, "mob." + (type == EntitySound.IDLE ? "magmacube" : "slime") + ".small", "small");
        }
        this.setSound(EntitySound.ATTACK, "mob.attack");
    }
}