package io.snw.entityapi.entity.type;

import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import io.snw.entityapi.entity.ControllableBaseEntity;
import net.minecraft.server.v1_7_R1.EntityBat;
import org.bukkit.entity.Bat;

public class ControllableBat extends ControllableBaseEntity<Bat> {

    public ControllableBat(ControllableBatEntity entityHandle) {
        super(ControllableEntityType.BAT);
        this.handle = entityHandle;
    }

    public void setHanging(boolean flag) {
        ((EntityBat) this.handle).a(flag);
    }

    public boolean isHanging() {
        return ((EntityBat) this.handle).bN();
    }

    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.bat.idle");
        this.setSound(EntitySound.HURT, "mob.bat.hurt");
        this.setSound(EntitySound.DEATH, "mob.bat.death");
    }
}