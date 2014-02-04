package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.Spider;

public class ControllableSpider extends ControllableAttackingBaseEntity<Spider> {

    public ControllableSpider(int id, EntityManager entityManager) {
        super(id, ControllableEntityType.SPIDER, entityManager);
    }

    public ControllableSpider(int id, ControllableCaveSpiderEntity entityHandle, EntityManager entityManager) {
        super(id, ControllableEntityType.SPIDER, entityManager);
        this.handle = entityHandle;
        this.loot = entityHandle.getDefaultMaterialLoot();
    }

    public void initSounds() {
        this.setSound(EntitySound.IDLE, "mob.spider.say");
        this.setSound(EntitySound.HURT, "mob.spider.say");
        this.setSound(EntitySound.DEATH, "mob.spider.death");
        this.setSound(EntitySound.STEP, "mob.spider.step");
    }
}