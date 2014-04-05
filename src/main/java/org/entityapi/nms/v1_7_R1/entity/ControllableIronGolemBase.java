package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.IronGolem;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;
import org.entityapi.api.entity.ControllableIronGolem;

public class ControllableIronGolemBase extends ControllableBaseEntity<IronGolem, ControllableIronGolemEntity> implements ControllableIronGolem {


    public ControllableIronGolemBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.IRON_GOLEM, manager);
    }

    public ControllableIronGolemBase(int id, ControllableIronGolemEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.HURT, "mob.irongolem.hit");
        this.setSound(EntitySound.DEATH, "mob.irongolem.death");
        this.setSound(EntitySound.THROW, "mob.irongolem.throw");
    }
}