package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import net.minecraft.server.v1_7_R1.EntityCow;
import org.bukkit.entity.Cow;

public class ControllableCow extends ControllableBaseEntity<Cow> {

    public ControllableCow(int id, EntityManager manager) {
        super(id, ControllableEntityType.COW, manager);
    }

    public ControllableCow(int id, ControllableBatEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public int getAge() {
        return ((EntityCow) this.handle).getAge();
    }
    
    public boolean isBaby() {
        return ((EntityCow) this.handle).isBaby();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.cow.say");
        this.setSound(EntitySound.HURT, "mob.cow.hurt");
        this.setSound(EntitySound.DEATH, "mob.cow.hurt");
        this.setSound(EntitySound.STEP, "mob.chicken.step");
    }
}