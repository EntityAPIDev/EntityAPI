package org.entityapi.api;

import org.entityapi.api.utils.ReflectionUtil;
import org.entityapi.internal.Constants;

import java.util.regex.Pattern;

public enum ControllableEntityType {

    HUMAN("Player", "Human", -1, true),
    BAT("Bat", Constants.EntityTypes.Names.ENTITY_BAT, Constants.EntityTypes.Ids.ENTITY_BAT, false),
    BLAZE("Blaze", Constants.EntityTypes.Names.ENTITY_BLAZE, Constants.EntityTypes.Ids.ENTITY_BLAZE, false),
    CAVE_SPIDER("CaveSpider", Constants.EntityTypes.Names.ENTITY_CAVE_SPIDER, Constants.EntityTypes.Ids.ENTITY_CAVE_SPIDER, false),
    CHICKEN("Chicken", Constants.EntityTypes.Names.ENTITY_CHICKEN, Constants.EntityTypes.Ids.ENTITY_CHICKEN, false),
    COW("Cow", Constants.EntityTypes.Names.ENTITY_COW, Constants.EntityTypes.Ids.ENTITY_COW, false),
    CREEPER("Creeper", Constants.EntityTypes.Names.ENTITY_CREEPER, Constants.EntityTypes.Ids.ENTITY_CREEPER, false),
    ENDERDRAGON("EnderDragon", Constants.EntityTypes.Names.ENTITY_ENDERDRAGON, Constants.EntityTypes.Ids.ENTITY_ENDERDRAGON, false),
    ENDERMAN("Enderman", Constants.EntityTypes.Names.ENTITY_ENDERMAN, Constants.EntityTypes.Ids.ENTITY_ENDERMAN, false),
    GHAST("Ghast", Constants.EntityTypes.Names.ENTITY_GHAST, Constants.EntityTypes.Ids.ENTITY_GHAST, false),
    GIANT("GiantZombie", Constants.EntityTypes.Names.ENTITY_GIANT, Constants.EntityTypes.Ids.ENTITY_GIANT, false),
    HORSE("Horse", Constants.EntityTypes.Names.ENTITY_HORSE, Constants.EntityTypes.Ids.ENTITY_HORSE, false),
    IRON_GOLEM("IronGolem", Constants.EntityTypes.Names.ENTITY_IRON_GOLEM, Constants.EntityTypes.Ids.ENTITY_IRON_GOLEM, false),
    LAVA_SLIME("MagmaCube", Constants.EntityTypes.Names.ENTITY_LAVA_SLIME, Constants.EntityTypes.Ids.ENTITY_LAVA_SLIME, false),
    MUSHROOMCOW("MuchroomCow", Constants.EntityTypes.Names.ENTITY_MUSHROOM_COW, Constants.EntityTypes.Ids.ENTITY_MUSHROOM_COW, false),
    OZELOT("Ocelot", Constants.EntityTypes.Names.ENTITY_OZELOT, Constants.EntityTypes.Ids.ENTITY_OZELOT, false),
    PIG("Pig", Constants.EntityTypes.Names.ENTITY_PIG, Constants.EntityTypes.Ids.ENTITY_PIG, false),
    PIG_ZOMBIE("PigZombie", Constants.EntityTypes.Names.ENTITY_PIG_ZOMBIE, Constants.EntityTypes.Ids.ENTITY_PIG_ZOMBIE, false),
    SHEEP("Sheep", Constants.EntityTypes.Names.ENTITY_SHEEP, Constants.EntityTypes.Ids.ENTITY_SHEEP, false),
    SILVERFISH("Silverfish", Constants.EntityTypes.Names.ENTITY_SILVERFISH, Constants.EntityTypes.Ids.ENTITY_SILVERFISH, false),
    SKELETON("Skeleton", Constants.EntityTypes.Names.ENTITY_SKELETON, Constants.EntityTypes.Ids.ENTITY_SKELETON, false),
    SLIME("Slime", Constants.EntityTypes.Names.ENTITY_SLIME, Constants.EntityTypes.Ids.ENTITY_SLIME, false),
    SNOWMAN("Snowman", Constants.EntityTypes.Names.ENTITY_SNOWMAN, Constants.EntityTypes.Ids.ENTITY_SNOWMAN, false),
    SPIDER("Spider", Constants.EntityTypes.Names.ENTITY_SPIDER, Constants.EntityTypes.Ids.ENTITY_SPIDER, false),
    SQUID("Squid", Constants.EntityTypes.Names.ENTITY_SQUID, Constants.EntityTypes.Ids.ENTITY_SQUID, false),
    VILLAGER("Villager", Constants.EntityTypes.Names.ENTITY_VILLAGER, Constants.EntityTypes.Ids.ENTITY_VILLAGER, false),
    WITCH("Witch", Constants.EntityTypes.Names.ENTITY_WITCH, Constants.EntityTypes.Ids.ENTITY_WITCH, false),
    WITHER("Wither", Constants.EntityTypes.Names.ENTITY_WITHER, Constants.EntityTypes.Ids.ENTITY_WITHER, false),
    WOLF("Wolf", Constants.EntityTypes.Names.ENTITY_WOLF, Constants.EntityTypes.Ids.ENTITY_WOLF, false),
    ZOMBIE("Zombie", Constants.EntityTypes.Names.ENTITY_ZOMBIE, Constants.EntityTypes.Ids.ENTITY_ZOMBIE, false);

    private final String name;
    private final int id;
    private final Class<? extends ControllableEntity> controllableClass;
    private final Class handleClass;
    private final boolean isNameRequired;

    ControllableEntityType(String classPath, String name, int id, boolean isNameRequired) {
        this.controllableClass = ReflectionUtil.getControllableEntityClass(classPath, false);
        this.handleClass = ReflectionUtil.getControllableEntityClass(classPath, true);
        if (!ControllableEntityHandle.class.isAssignableFrom(handleClass))
            throw new RuntimeException("Handle class needs to implement ControllableEntityHandle!");

        Pattern p = Pattern.compile("org\\.entityapi\\.[^api]");
        this.name = name;
        this.id = id;
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

    public static ControllableEntityType getByEntityClass(Class clazz) {
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