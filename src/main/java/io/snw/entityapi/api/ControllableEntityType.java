package io.snw.entityapi.api;

import io.snw.entityapi.entity.*;
import io.snw.entityapi.internal.Constants;

public enum ControllableEntityType {

    BAT(Constants.EntityTypes.Names.ENTITY_BAT, Constants.EntityTypes.Ids.ENTITY_BAT, ControllableBat.class, ControllableBatEntity.class),
    BLAZE(Constants.EntityTypes.Names.ENTITY_BLAZE, Constants.EntityTypes.Ids.ENTITY_BLAZE, ControllableBlaze.class, ControllableBlazeEntity.class),
    CAVE_SPIDER(Constants.EntityTypes.Names.ENTITY_CAVE_SPIDER, Constants.EntityTypes.Ids.ENTITY_CAVE_SPIDER, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    CHICKEN(Constants.EntityTypes.Names.ENTITY_CHICKEN, Constants.EntityTypes.Ids.ENTITY_CHICKEN, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    COW(Constants.EntityTypes.Names.ENTITY_COW, Constants.EntityTypes.Ids.ENTITY_COW, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    CREEPER(Constants.EntityTypes.Names.ENTITY_CREEPER, Constants.EntityTypes.Ids.ENTITY_CREEPER, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    ENDERDRAGON(Constants.EntityTypes.Names.ENTITY_ENDERDRAGON, Constants.EntityTypes.Ids.ENTITY_ENDERDRAGON, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    ENDERMAN(Constants.EntityTypes.Names.ENTITY_ENDERMAN, Constants.EntityTypes.Ids.ENTITY_ENDERMAN, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    GHAST(Constants.EntityTypes.Names.ENTITY_GHAST, Constants.EntityTypes.Ids.ENTITY_GHAST, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    GIANT(Constants.EntityTypes.Names.ENTITY_GIANT, Constants.EntityTypes.Ids.ENTITY_GIANT, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    HORSE(Constants.EntityTypes.Names.ENTITY_HORSE, Constants.EntityTypes.Ids.ENTITY_HORSE, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    IRON_GOLEM(Constants.EntityTypes.Names.ENTITY_IRON_GOLEM, Constants.EntityTypes.Ids.ENTITY_IRON_GOLEM, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    LAVA_SLIME(Constants.EntityTypes.Names.ENTITY_LAVA_SLIME, Constants.EntityTypes.Ids.ENTITY_LAVA_SLIME, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    MUSHROOMCOW(Constants.EntityTypes.Names.ENTITY_MUSHROOM_COW, Constants.EntityTypes.Ids.ENTITY_MUSHROOM_COW, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    OZELOT(Constants.EntityTypes.Names.ENTITY_OZELOT, Constants.EntityTypes.Ids.ENTITY_OZELOT, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    PIG(Constants.EntityTypes.Names.ENTITY_PIG, Constants.EntityTypes.Ids.ENTITY_PIG, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    PIG_ZOMBIE(Constants.EntityTypes.Names.ENTITY_PIG_ZOMBIE, Constants.EntityTypes.Ids.ENTITY_PIG_ZOMBIE, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    SHEEP(Constants.EntityTypes.Names.ENTITY_SHEEP, Constants.EntityTypes.Ids.ENTITY_SHEEP, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    SILVERFISH(Constants.EntityTypes.Names.ENTITY_SILVERFISH, Constants.EntityTypes.Ids.ENTITY_SILVERFISH, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    SKELETON(Constants.EntityTypes.Names.ENTITY_SKELETON, Constants.EntityTypes.Ids.ENTITY_SKELETON, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    SLIME(Constants.EntityTypes.Names.ENTITY_SLIME, Constants.EntityTypes.Ids.ENTITY_SLIME, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    SNOWMAN(Constants.EntityTypes.Names.ENTITY_SNOWMAN, Constants.EntityTypes.Ids.ENTITY_SNOWMAN, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    SPIDER(Constants.EntityTypes.Names.ENTITY_SPIDER, Constants.EntityTypes.Ids.ENTITY_SPIDER, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    SQUID(Constants.EntityTypes.Names.ENTITY_SQUID, Constants.EntityTypes.Ids.ENTITY_SQUID, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    VILLAGER(Constants.EntityTypes.Names.ENTITY_VILLAGER, Constants.EntityTypes.Ids.ENTITY_VILLAGER, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    WITCH(Constants.EntityTypes.Names.ENTITY_WITCH, Constants.EntityTypes.Ids.ENTITY_WITCH, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    WITHER(Constants.EntityTypes.Names.ENTITY_WITHER, Constants.EntityTypes.Ids.ENTITY_WITHER, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    WOLF(Constants.EntityTypes.Names.ENTITY_WOLF, Constants.EntityTypes.Ids.ENTITY_WOLF, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    ZOMBIE(Constants.EntityTypes.Names.ENTITY_ZOMBIE, Constants.EntityTypes.Ids.ENTITY_ZOMBIE, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class);

    private final String name;
    private final int id;
    private final Class<? extends ControllableBaseEntity> entityClass;
    private final Class<? extends ControllableEntityHandle> handleClass;

    ControllableEntityType(String name, int id, Class<? extends ControllableBaseEntity> entityClass, Class<? extends ControllableEntityHandle> handleClass) {
        this.name = name;
        this.id = id;
        this.entityClass = entityClass;
        this.handleClass = handleClass;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public Class<? extends ControllableBaseEntity> getEntityClass() {
        return this.entityClass;
    }

    public Class<? extends ControllableEntityHandle> getHandleClass() {
        return this.handleClass;
    }
}