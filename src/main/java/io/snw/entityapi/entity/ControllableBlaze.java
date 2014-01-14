package io.snw.entityapi.entity;

import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import net.minecraft.server.v1_7_R1.EntityBlaze;
import org.bukkit.Material;
import org.bukkit.entity.Blaze;

public class ControllableBlaze extends ControllableAttackingBaseEntity<Blaze> {

    public ControllableBlaze(ControllableBlazeEntity entityHandle) {
        super(ControllableEntityType.BLAZE);
        this.handle = entityHandle;
        this.loot = Material.BLAZE_ROD;
    }

    public void setOnFire(boolean flag) {
        ((EntityBlaze) this.handle).a(flag);
    }

    public boolean isHanging() {
        return ((EntityBlaze) this.handle).bX();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.blaze.breathe");
        this.setSound(EntitySound.HURT, "mob.blaze.hit");
        this.setSound(EntitySound.DEATH, "mob.blaze.death");
    }
}