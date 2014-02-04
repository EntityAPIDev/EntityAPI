package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.Slime;

public class ControllableSlime extends ControllableAttackingBaseEntity<Slime> {

    public ControllableSlime(int id, EntityManager entityManager) {
        super(id, ControllableEntityType.SLIME, entityManager);
    }

    public ControllableSlime(int id, ControllableSlimeEntity entityHandle, EntityManager entityManager) {
        super(id, ControllableEntityType.SLIME, entityManager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public void initSounds() {
        for (EntitySound type : new EntitySound[] {EntitySound.IDLE, EntitySound.HURT, EntitySound.DEATH}) {
            this.setSound(type, "mob.slime.big", "big");
            this.setSound(type, "mob.slime.small", "small");
        }
        this.setSound(EntitySound.ATTACK, "mob.attack");
    }
}