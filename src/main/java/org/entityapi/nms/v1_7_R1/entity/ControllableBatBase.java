package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.Bat;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;
import org.entityapi.api.entity.ControllableBat;

public class ControllableBatBase extends ControllableBaseEntity<Bat, ControllableBatEntity> implements ControllableBat {

    public ControllableBatBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.BAT, manager);
    }

    public ControllableBatBase(int id, ControllableBatEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void setHanging(boolean flag) {
        this.handle.setHanging(flag);
    }

    @Override
    public boolean isHanging() {
        return this.handle.isHanging();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.bat.idle");
        this.setSound(EntitySound.HURT, "mob.bat.hurt");
        this.setSound(EntitySound.DEATH, "mob.bat.death");
    }
}