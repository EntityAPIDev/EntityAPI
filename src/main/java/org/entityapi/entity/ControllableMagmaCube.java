package org.entityapi.entity;

import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;
import org.bukkit.entity.MagmaCube;

public class ControllableMagmaCube extends ControllableBaseEntity<MagmaCube> {

    public ControllableMagmaCube(int id, EntityManager manager) {
        super(id, ControllableEntityType.LAVA_SLIME, manager);
    }

    public ControllableMagmaCube(int id, ControllableMagmaCubeEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        for (EntitySound type : new EntitySound[]{EntitySound.IDLE, EntitySound.HURT, EntitySound.DEATH}) {
            this.setSound(type, "mob." + (type == EntitySound.IDLE ? "magmacube" : "slime") + ".big", "big");
            this.setSound(type, "mob." + (type == EntitySound.IDLE ? "magmacube" : "slime") + ".small", "small");
        }
        this.setSound(EntitySound.ATTACK, "mob.attack");
    }
}