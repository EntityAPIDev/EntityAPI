package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.Ocelot;

public class ControllableOcelot extends ControllableBaseEntity<Ocelot> {

    public ControllableOcelot(int id, EntityManager entityManager) {
        super(id, ControllableEntityType.OZELOT, entityManager);
    }

    public ControllableOcelot(int id, ControllableOcelotEntity entityHandle, EntityManager entityManager) {
        super(id, ControllableEntityType.OZELOT, entityManager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.cat.purr", "purr");
        this.setSound(EntitySound.IDLE, "mob.cat.purreow", "purreow");
        this.setSound(EntitySound.IDLE, "mob.cat.meow", "meow");
        this.setSound(EntitySound.HURT, "mob.cat.hitt");
        this.setSound(EntitySound.DEATH, "mob.cat.hitt");
    }
}