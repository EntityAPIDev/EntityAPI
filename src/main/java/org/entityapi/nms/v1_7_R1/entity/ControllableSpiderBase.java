package org.entityapi.nms.v1_7_R1.entity;

import org.bukkit.entity.Spider;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntityManager;
import org.entityapi.api.EntitySound;
import org.entityapi.api.entity.ControllableSpider;

public class ControllableSpiderBase extends ControllableBaseEntity<Spider, ControllableSpiderEntity> implements ControllableSpider {

    public ControllableSpiderBase(int id, EntityManager manager) {
        super(id, ControllableEntityType.SPIDER, manager);
    }

    public ControllableSpiderBase(int id, ControllableSpiderEntity entityHandle, EntityManager manager) {
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