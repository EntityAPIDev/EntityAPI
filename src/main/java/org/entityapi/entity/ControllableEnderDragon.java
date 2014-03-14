package org.entityapi.entity;

import org.bukkit.entity.EnderDragon;
import org.bukkit.util.Vector;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;

public class ControllableEnderDragon extends ControllableBaseEntity<EnderDragon, ControllableEnderDragonEntity> {

    private boolean useAppliedTargetPosition;
    private Vector targetPosition;

    public ControllableEnderDragon(int id, EntityManager manager) {
        super(id, ControllableEntityType.ENDERDRAGON, manager);
    }

    public ControllableEnderDragon(int id, ControllableEnderDragonEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public boolean shouldUseAppliedTargetPosition() {
        return useAppliedTargetPosition;
    }

    public void setUsingAppliedTargetPosition(boolean flag) {
        this.useAppliedTargetPosition = flag;
    }

    public Vector getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(Vector position) {
        this.targetPosition = position;
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.enderdragon.growl");
        this.setSound(EntitySound.HIT, "mob.enderdragon.hit");
        this.setSound(EntitySound.DEATH, "mob.enderdragon.end");
        this.setSound(EntitySound.WINGS, "mob.enderdragon.wings");
    }
}