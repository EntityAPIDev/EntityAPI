package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.Creeper;

public class ControllableCreeper extends ControllableBaseEntity<Creeper> {

    public ControllableCreeper(int id, EntityManager entityManager) {
        super(id, ControllableEntityType.CREEPER, entityManager);
    }

    public ControllableCreeper(int id, ControllableCreeperEntity entityHandle, EntityManager entityManager) {
        super(id, ControllableEntityType.CREEPER, entityManager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public void explode(int modifier) {
        if (this.handle != null) {
            ((ControllableCreeperEntity) this.handle).explode(modifier);
        }
    }

    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.creeper.say");
        this.setSound(EntitySound.DEATH, "mob.creeper.death");
    }
}