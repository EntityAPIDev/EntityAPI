package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.Villager;

public class ControllableVillager extends ControllableBaseEntity<Villager> {

    public ControllableVillager(int id, EntityManager manager) {
        super(id, ControllableEntityType.VILLAGER, manager);
    }

    public ControllableVillager(int id, ControllableVillagerEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "haggle", "mob.villager.haggle");
        this.setSound(EntitySound.IDLE, "idle", "mob.villager.idle");
        this.setSound(EntitySound.HURT, "mob.villager.hit");
        this.setSound(EntitySound.DEATH, "mob.villager.death");
        this.setSound(EntitySound.YES, "mob.villager.yes");
        this.setSound(EntitySound.NO, "mob.villager.no");
    }
}