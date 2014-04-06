package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.Zombie;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntityManager;
import org.entityapi.api.EntitySound;
import org.entityapi.api.entity.ControllableZombie;

public class ControllableZombieBase extends ControllableBaseEntity<Zombie, ControllableZombieEntity> implements ControllableZombie {

    public ControllableZombieBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.ZOMBIE, manager);
    }

    public ControllableZombieBase(int id, ControllableZombieEntity entityHandle, EntityManager manager) {
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