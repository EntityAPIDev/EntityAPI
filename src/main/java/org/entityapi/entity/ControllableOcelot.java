package org.entityapi.entity;

import org.bukkit.entity.Ocelot;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;

public class ControllableOcelot extends ControllableBaseEntity<Ocelot, ControllableOcelotEntity> {

    public ControllableOcelot(int id, EntityManager manager) {
        super(id, ControllableEntityType.OZELOT, manager);
    }

    public ControllableOcelot(int id, ControllableOcelotEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.cat.purr", "purr");
        this.setSound(EntitySound.IDLE, "mob.cat.purreow", "purreow");
        this.setSound(EntitySound.IDLE, "mob.cat.meow", "meow");
        this.setSound(EntitySound.HURT, "mob.cat.hitt");
        this.setSound(EntitySound.DEATH, "mob.cat.hitt");
    }
}