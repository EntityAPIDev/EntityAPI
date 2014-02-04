package io.snw.entityapi.entity;

import io.snw.entityapi.EntityManager;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import org.bukkit.entity.CaveSpider;

public class ControllableCaveSpider extends ControllableAttackingBaseEntity<CaveSpider> {

    public ControllableCaveSpider(int id, EntityManager manager) {
        super(id, ControllableEntityType.CAVE_SPIDER, manager);
    }

    public ControllableCaveSpider(int id, ControllableCaveSpiderEntity entityHandle, EntityManager manager) {
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