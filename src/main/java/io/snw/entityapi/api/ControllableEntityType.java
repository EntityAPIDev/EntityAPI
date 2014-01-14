package io.snw.entityapi.api;

import io.snw.entityapi.entity.*;
import io.snw.entityapi.internal.Constants;

public enum ControllableEntityType {

    BAT(Constants.EntityTypes.Names.ENTITY_BAT, Constants.EntityTypes.Ids.ENTITY_BAT, ControllableBat.class, ControllableBatEntity.class),
    BLAZE(Constants.EntityTypes.Names.ENTITY_BLAZE, Constants.EntityTypes.Ids.ENTITY_BLAZE, ControllableBlaze.class, ControllableBlazeEntity.class),
    CAVE_SPIDER(Constants.EntityTypes.Names.ENTTY_CAVE_SPIDER, Constants.EntityTypes.Ids.ENTTY_CAVE_SPIDER, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class),
    SPIDER(Constants.EntityTypes.Names.ENTITY_SPIDER, Constants.EntityTypes.Ids.ENTITY_SPIDER, ControllableSpider.class, ControllableSpiderEntity.class);

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