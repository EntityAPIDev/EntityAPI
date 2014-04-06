package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.Creeper;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntityManager;
import org.entityapi.api.EntitySound;
import org.entityapi.api.entity.ControllableCreeper;

public class ControllableCreeperBase extends ControllableBaseEntity<Creeper, ControllableCreeperEntity> implements ControllableCreeper {

    public ControllableCreeperBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.CREEPER, manager);
    }

    public ControllableCreeperBase(int id, ControllableCreeperEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void explode(int modifier) {
        if (this.handle != null) {
            this.handle.explode(modifier);
        }
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.creeper.say");
        this.setSound(EntitySound.DEATH, "mob.creeper.death");
    }
}