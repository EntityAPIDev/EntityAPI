package org.entityapi.entity;

import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;
import org.bukkit.entity.Giant;

public class ControllableGiantZombie extends ControllableBaseEntity<Giant> {

    public ControllableGiantZombie(int id, EntityManager manager) {
        super(id, ControllableEntityType.GIANT, manager);
    }

    public ControllableGiantZombie(int id, ControllableGiantZombieEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.zombie.say");
        this.setSound(EntitySound.HURT, "mob.zombie.hurt");
        this.setSound(EntitySound.DEATH, "mob.zombie.death");
        this.setSound(EntitySound.STEP, "mob.zombie.step");
    }
}