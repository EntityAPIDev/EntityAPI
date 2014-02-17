package org.entityapi.entity;

import org.bukkit.entity.Creeper;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;

public class ControllableCreeper extends ControllableBaseEntity<Creeper, ControllableCreeperEntity> {

    public ControllableCreeper(int id, EntityManager manager) {
        super(id, ControllableEntityType.CREEPER, manager);
    }

    public ControllableCreeper(int id, ControllableCreeperEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public void explode(int modifier) {
        if (this.handle != null) {
            ((ControllableCreeperEntity) this.handle).explode(modifier);
        }
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.creeper.say");
        this.setSound(EntitySound.DEATH, "mob.creeper.death");
    }
}