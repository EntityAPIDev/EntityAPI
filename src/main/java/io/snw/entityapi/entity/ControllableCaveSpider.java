package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.Spider;

public class ControllableCaveSpider extends ControllableAttackingBaseEntity<Spider> {

    public ControllableCaveSpider(int id, EntityManager entityManager) {
        super(id, ControllableEntityType.CAVE_SPIDER, entityManager);
    }

    public ControllableCaveSpider(int id, ControllableCaveSpiderEntity entityHandle, EntityManager entityManager) {
        super(id, ControllableEntityType.CAVE_SPIDER, entityManager);
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