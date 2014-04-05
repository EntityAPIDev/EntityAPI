package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.Giant;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntityManager;
import org.entityapi.api.EntitySound;
import org.entityapi.api.entity.ControllableGiantZombie;

public class ControllableGiantZombieBase extends ControllableBaseEntity<Giant, ControllableGiantZombieEntity> implements ControllableGiantZombie {

    public ControllableGiantZombieBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.GIANT, manager);
    }

    public ControllableGiantZombieBase(int id, ControllableGiantZombieEntity entityHandle, EntityManager manager) {
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