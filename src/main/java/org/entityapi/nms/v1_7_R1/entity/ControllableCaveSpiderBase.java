package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.CaveSpider;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;
import org.entityapi.api.entity.ControllableCaveSpider;

public class ControllableCaveSpiderBase extends ControllableBaseEntity<CaveSpider, ControllableCaveSpiderEntity> implements ControllableCaveSpider {

    public ControllableCaveSpiderBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.CAVE_SPIDER, manager);
    }

    public ControllableCaveSpiderBase(int id, ControllableCaveSpiderEntity entityHandle, EntityManager manager) {
        this(id, manager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    @Override
    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.spider.say");
        this.setSound(EntitySound.HURT, "mob.spider.say");
        this.setSound(EntitySound.DEATH, "mob.spider.death");
        this.setSound(EntitySound.STEP, "mob.spider.step");
    }
}