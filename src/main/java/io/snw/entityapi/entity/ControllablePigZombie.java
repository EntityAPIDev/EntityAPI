package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.PigZombie;

public class ControllablePigZombie extends ControllableAttackingBaseEntity<PigZombie> {

    public ControllablePigZombie(int id, EntityManager entityManager) {
        super(id, ControllableEntityType.PIG_ZOMBIE, entityManager);
    }

    public ControllablePigZombie(int id, ControllablePigZombieEntity entityHandle, EntityManager entityManager) {
        super(id, ControllableEntityType.PIG_ZOMBIE, entityManager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.zombiepig.zpig");
        this.setSound(EntitySound.HURT, "mob.zombiepig.zpighurt");
        this.setSound(EntitySound.DEATH, "mob.zombiepig.zpigdeath");
        this.setSound(EntitySound.STEP, "mob.zombie.step");
        this.setSound(EntitySound.ANGRY, "mob.zombiepig.zpigangry");
    }
}