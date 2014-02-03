package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import net.minecraft.server.v1_7_R1.EntityCreeper;
import net.minecraft.server.v1_7_R1.NBTTagCompound;
import org.bukkit.entity.Creeper;

public class ControllableCreeper extends ControllableBaseEntity<Creeper> {

    public ControllableCreeper(int id, EntityManager manager) {
        super(id, ControllableEntityType.CREEPER, manager);
    }

    public ControllableCreeper(int id, ControllableBatEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public boolean isPowered() {
        return ((EntityCreeper) this.handle).isPowered();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.creeper.say");
        this.setSound(EntitySound.DEATH, "mob.creeper.death");
    }
}