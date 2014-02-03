package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import net.minecraft.server.v1_7_R1.EntityCow;
import org.bukkit.entity.Cow;

public class ControllableCow extends ControllableBaseEntity<Cow> {

    public ControllableCow(int id, EntityManager manager) {
        super(id, ControllableEntityType.CHICKEN, manager);
    }

    public ControllableCow(int id, ControllableBatEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public void setHanging(boolean flag) {
        ((EntityCow) this.handle).a(flag);
    }

    public boolean isHanging() {
        return ((EntityCow) this.handle).bk();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.cow.say");
        this.setSound(EntitySound.HURT, "mob.cow.hurt");
        this.setSound(EntitySound.DEATH, "mob.cow.hurt");
        this.setSound(EntitySound.STEP, "mob.chicken.step");
    }
}