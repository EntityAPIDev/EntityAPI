package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.MagmaCube;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;
import org.entityapi.api.entity.ControllableMagmaCube;

public class ControllableMagmaCubeBase extends ControllableBaseEntity<MagmaCube, ControllableMagmaCubeEntity> implements ControllableMagmaCube {

    public ControllableMagmaCubeBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.LAVA_SLIME, manager);
    }

    public ControllableMagmaCubeBase(int id, ControllableMagmaCubeEntity entityHandle, EntityManager manager) {
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