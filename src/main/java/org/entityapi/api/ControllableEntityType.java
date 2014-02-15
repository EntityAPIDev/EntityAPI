package org.entityapi.api;

import org.entityapi.internal.Constants;
import net.minecraft.server.v1_7_R1.EntityLiving;
import org.entityapi.entity.*;

public enum ControllableEntityType {

    HUMAN("Human", -1, ControllablePlayer.class, ControllablePlayerEntity.class, true),
    BAT(Constants.EntityTypes.Names.ENTITY_BAT, Constants.EntityTypes.Ids.ENTITY_BAT, ControllableBat.class, ControllableBatEntity.class, false),
    BLAZE(Constants.EntityTypes.Names.ENTITY_BLAZE, Constants.EntityTypes.Ids.ENTITY_BLAZE, ControllableBlaze.class, ControllableBlazeEntity.class, false),
    CAVE_SPIDER(Constants.EntityTypes.Names.ENTITY_CAVE_SPIDER, Constants.EntityTypes.Ids.ENTITY_CAVE_SPIDER, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    CHICKEN(Constants.EntityTypes.Names.ENTITY_CHICKEN, Constants.EntityTypes.Ids.ENTITY_CHICKEN, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    COW(Constants.EntityTypes.Names.ENTITY_COW, Constants.EntityTypes.Ids.ENTITY_COW, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    CREEPER(Constants.EntityTypes.Names.ENTITY_CREEPER, Constants.EntityTypes.Ids.ENTITY_CREEPER, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    ENDERDRAGON(Constants.EntityTypes.Names.ENTITY_ENDERDRAGON, Constants.EntityTypes.Ids.ENTITY_ENDERDRAGON, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    ENDERMAN(Constants.EntityTypes.Names.ENTITY_ENDERMAN, Constants.EntityTypes.Ids.ENTITY_ENDERMAN, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    GHAST(Constants.EntityTypes.Names.ENTITY_GHAST, Constants.EntityTypes.Ids.ENTITY_GHAST, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    GIANT(Constants.EntityTypes.Names.ENTITY_GIANT, Constants.EntityTypes.Ids.ENTITY_GIANT, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    HORSE(Constants.EntityTypes.Names.ENTITY_HORSE, Constants.EntityTypes.Ids.ENTITY_HORSE, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    IRON_GOLEM(Constants.EntityTypes.Names.ENTITY_IRON_GOLEM, Constants.EntityTypes.Ids.ENTITY_IRON_GOLEM, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    LAVA_SLIME(Constants.EntityTypes.Names.ENTITY_LAVA_SLIME, Constants.EntityTypes.Ids.ENTITY_LAVA_SLIME, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    MUSHROOMCOW(Constants.EntityTypes.Names.ENTITY_MUSHROOM_COW, Constants.EntityTypes.Ids.ENTITY_MUSHROOM_COW, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    OZELOT(Constants.EntityTypes.Names.ENTITY_OZELOT, Constants.EntityTypes.Ids.ENTITY_OZELOT, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    PIG(Constants.EntityTypes.Names.ENTITY_PIG, Constants.EntityTypes.Ids.ENTITY_PIG, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    PIG_ZOMBIE(Constants.EntityTypes.Names.ENTITY_PIG_ZOMBIE, Constants.EntityTypes.Ids.ENTITY_PIG_ZOMBIE, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    SHEEP(Constants.EntityTypes.Names.ENTITY_SHEEP, Constants.EntityTypes.Ids.ENTITY_SHEEP, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    SILVERFISH(Constants.EntityTypes.Names.ENTITY_SILVERFISH, Constants.EntityTypes.Ids.ENTITY_SILVERFISH, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    SKELETON(Constants.EntityTypes.Names.ENTITY_SKELETON, Constants.EntityTypes.Ids.ENTITY_SKELETON, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    SLIME(Constants.EntityTypes.Names.ENTITY_SLIME, Constants.EntityTypes.Ids.ENTITY_SLIME, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    SNOWMAN(Constants.EntityTypes.Names.ENTITY_SNOWMAN, Constants.EntityTypes.Ids.ENTITY_SNOWMAN, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    SPIDER(Constants.EntityTypes.Names.ENTITY_SPIDER, Constants.EntityTypes.Ids.ENTITY_SPIDER, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    SQUID(Constants.EntityTypes.Names.ENTITY_SQUID, Constants.EntityTypes.Ids.ENTITY_SQUID, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    VILLAGER(Constants.EntityTypes.Names.ENTITY_VILLAGER, Constants.EntityTypes.Ids.ENTITY_VILLAGER, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    WITCH(Constants.EntityTypes.Names.ENTITY_WITCH, Constants.EntityTypes.Ids.ENTITY_WITCH, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    WITHER(Constants.EntityTypes.Names.ENTITY_WITHER, Constants.EntityTypes.Ids.ENTITY_WITHER, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    WOLF(Constants.EntityTypes.Names.ENTITY_WOLF, Constants.EntityTypes.Ids.ENTITY_WOLF, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false),
    ZOMBIE(Constants.EntityTypes.Names.ENTITY_ZOMBIE, Constants.EntityTypes.Ids.ENTITY_ZOMBIE, ControllableCaveSpider.class, ControllableCaveSpiderEntity.class, false);

    private final String name;
    private final int id;
    private final Class<? extends ControllableEntity> controllableClass;
    private final Class handleClass;
    private final boolean isNameRequired;

    ControllableEntityType(String name, int id, Class<? extends ControllableEntity> controllableClass, Class handleClass, boolean isNameRequired) {
        if(!ControllableEntityHandle.class.isAssignableFrom(handleClass))
            throw new RuntimeException("Handle class needs to implement ControllableEntityHandle!");

        this.name = name;
        this.id = id;
        this.controllableClass = controllableClass;
        this.handleClass = handleClass;
        this.isNameRequired = isNameRequired;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public boolean isNameRequired() {
        return this.isNameRequired;
    }

    public Class<? extends ControllableEntity> getControllableClass() {
        return this.controllableClass;
    }

    public Class getHandleClass() {
        return this.handleClass;
    }

    public static ControllableEntityType getByControllableClass(Class<? extends ControllableEntity> clazz) {
        for (ControllableEntityType type : values()) {
            if (type.getHandleClass().equals(clazz))
                return type;
        }
        return null;
    }

    public static ControllableEntityType getByEntityClass(Class<? extends EntityLiving> clazz) {
        for (ControllableEntityType type : values()) {
            if (type.getControllableClass().equals(clazz) || type.getControllableClass().getSuperclass().equals(clazz) || type.getControllableClass().isAssignableFrom(clazz))
                return type;
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }
}