package org.entityapi.entity;

import org.bukkit.entity.Spider;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.EntitySound;

public class ControllableSpider extends ControllableBaseEntity<Spider, ControllableSpiderEntity> {

    public ControllableSpider(int id, EntityManager manager) {
        super(id, ControllableEntityType.SPIDER, manager);
    }

    public ControllableSpider(int id, ControllableSpiderEntity entityHandle, EntityManager manager) {
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