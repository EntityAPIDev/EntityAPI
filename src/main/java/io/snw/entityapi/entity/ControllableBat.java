package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import net.minecraft.server.v1_7_R1.EntityBat;
import org.bukkit.entity.Bat;

public class ControllableBat extends ControllableBaseEntity<Bat> {

    //TODO; Every entity class should look like this. (constructor)

    public ControllableBat(int id, EntityManager manager) {
        super(id, ControllableEntityType.BAT, manager);
    }

    public ControllableBat(int id, ControllableBatEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public void setHanging(boolean flag) {
        ((EntityBat) this.handle).a(flag);
    }

    public boolean isHanging() {
        return ((EntityBat) this.handle).bN();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.bat.idle");
        this.setSound(EntitySound.HURT, "mob.bat.hurt");
        this.setSound(EntitySound.DEATH, "mob.bat.death");
    }
}