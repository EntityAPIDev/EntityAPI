package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.Slime;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;
import org.entityapi.api.entity.ControllableSlime;

public class ControllableSlimeBase extends ControllableBaseEntity<Slime, ControllableSlimeEntity> implements ControllableSlime {

    public ControllableSlimeBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.SLIME, manager);
    }

    public ControllableSlimeBase(int id, ControllableSlimeEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        for (EntitySound type : new EntitySound[]{EntitySound.IDLE, EntitySound.HURT, EntitySound.DEATH}) {
            this.setSound(type, "mob.slime.big", "big");
            this.setSound(type, "mob.slime.small", "small");
        }
        this.setSound(EntitySound.ATTACK, "mob.attack");
    }
}