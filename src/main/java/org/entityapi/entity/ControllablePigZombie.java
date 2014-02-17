package org.entityapi.entity;

import org.bukkit.entity.PigZombie;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;

public class ControllablePigZombie extends ControllableBaseEntity<PigZombie, ControllablePigZombieEntity> {

    public ControllablePigZombie(int id, EntityManager manager) {
        super(id, ControllableEntityType.PIG_ZOMBIE, manager);
    }

    public ControllablePigZombie(int id, ControllablePigZombieEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.zombiepig.zpig");
        this.setSound(EntitySound.HURT, "mob.zombiepig.zpighurt");
        this.setSound(EntitySound.DEATH, "mob.zombiepig.zpigdeath");
        this.setSound(EntitySound.STEP, "mob.zombie.step");
        this.setSound(EntitySound.ANGRY, "mob.zombiepig.zpigangry");
    }
}