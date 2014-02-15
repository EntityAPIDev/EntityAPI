package org.entityapi.entity;

import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;
import org.bukkit.entity.Slime;

public class ControllableSlime extends ControllableBaseEntity<Slime> {

    public ControllableSlime(int id, EntityManager manager) {
        super(id, ControllableEntityType.SLIME, manager);
    }

    public ControllableSlime(int id, ControllableSlimeEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        for (EntitySound type : new EntitySound[]{EntitySound.IDLE, EntitySound.HURT, EntitySound.DEATH}) {
            this.setSound(type, "mob.slime.big", "big");
            this.setSound(type, "mob.slime.small", "small");
        }
        this.setSound(EntitySound.ATTACK, "mob.attack");
    }
}