package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.EnderDragon;
import org.bukkit.util.Vector;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;
import org.entityapi.api.entity.ControllableEnderDragon;

public class ControllableEnderDragonBase extends ControllableBaseEntity<EnderDragon, ControllableEnderDragonEntity> implements ControllableEnderDragon {

    private boolean useAppliedTargetPosition;
    private Vector targetPosition;

    public ControllableEnderDragonBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.ENDERDRAGON, manager);
    }

    public ControllableEnderDragonBase(int id, ControllableEnderDragonEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public boolean isUsingAppliedTargetPosition() {
        return useAppliedTargetPosition;
    }

    @Override
    public void setUsingAppliedTargetPosition(boolean flag) {
        this.useAppliedTargetPosition = flag;
    }

    @Override
    public Vector getTargetPosition() {
        return targetPosition;
    }

    @Override
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