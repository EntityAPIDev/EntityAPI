package io.snw.entityapi.hooks;

import io.snw.entityapi.internal.Constants;
import io.snw.entityapi.reflection.ClassTemplate;
import io.snw.entityapi.reflection.NMSClassTemplate;
import org.bukkit.entity.EntityType;

public enum NMSEntityType {

    DROPPED_ITEM(Constants.EntityTypes.Ids.ITEM, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ITEM), EntityType.DROPPED_ITEM),
    EXPERIENCE_ORB(Constants.EntityTypes.Ids.XP_ORB, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.XP_ORB), EntityType.EXPERIENCE_ORB),
    LEASH_HITCH(Constants.EntityTypes.Ids.LEASH, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.LEASH), EntityType.LEASH_HITCH),
    PAINTING(Constants.EntityTypes.Ids.PANTING, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.PANTING), EntityType.PAINTING),
    ARROW(Constants.EntityTypes.Ids.ARROW, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ARROW), EntityType.ARROW),
    SNOWBALL(Constants.EntityTypes.Ids.SNOWBALL, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.SNOWBALL), EntityType.SNOWBALL),
    FIREBALL(Constants.EntityTypes.Ids.FIREBALL, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.FIREBALL), EntityType.FIREBALL),
    SMALL_FIREBALL(Constants.EntityTypes.Ids.SMALL_FIREBALL, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.SMALL_FIREBALL), EntityType.SMALL_FIREBALL),
    ENDER_PEARL(Constants.EntityTypes.Ids.THROWN_ENDERPEARL, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.THROWN_ENDERPEARL), EntityType.ENDER_PEARL),
    ENDER_SIGNAL(Constants.EntityTypes.Ids.EYE_OF_ENDER_SIGNAL, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.EYE_OF_ENDER_SIGNAL), EntityType.ENDER_SIGNAL),
    THROWN_EXP_BOTTLE(Constants.EntityTypes.Ids.THROWN_XP_BOTTLE, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.THROWN_XP_BOTTLE), EntityType.THROWN_EXP_BOTTLE),
    ITEM_FRAME(Constants.EntityTypes.Ids.ITEMFRAME, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ITEMFRAME), EntityType.ITEM_FRAME),
    WITHER_SKULL(Constants.EntityTypes.Ids.WITHER_SKULL, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.WITHER_SKULL), EntityType.WITHER_SKULL),
    PRIMED_TNT(Constants.EntityTypes.Ids.PRIMED_TNT, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.PRIMED_TNT), EntityType.PRIMED_TNT),
    FALLING_BLOCK(Constants.EntityTypes.Ids.FALLING_SAND, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.FALLING_SAND), EntityType.FALLING_BLOCK),
    FIREWORK(Constants.EntityTypes.Ids.FIREWORKS_ROCKET, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.FIREWORKS_ROCKET), EntityType.FIREWORK),
    MINECART_COMMAND(Constants.EntityTypes.Ids.MINECART_COMMANDBLOCK, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.MINECART_COMMANDBLOCK), EntityType.MINECART_COMMAND),
    BOAT(Constants.EntityTypes.Ids.BOAT, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.BOAT), EntityType.BOAT),
    MINECART(Constants.EntityTypes.Ids.MINECART_RIDEABLE, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.MINECART_RIDEABLE), EntityType.MINECART),
    MINECART_CHEST(Constants.EntityTypes.Ids.MINECART_CHEST, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.MINECART_CHEST), EntityType.MINECART_CHEST),
    MINECART_FURNACE(Constants.EntityTypes.Ids.MINECART_FURNANCE, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.MINECART_FURNANCE), EntityType.MINECART_FURNACE),
    MINECART_TNT(Constants.EntityTypes.Ids.MINECART_TNT, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.MINECART_TNT), EntityType.MINECART_TNT),
    MINECART_HOPPER(Constants.EntityTypes.Ids.MINECART_HOPPER, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.MINECART_HOPPER), EntityType.MINECART_MOB_SPAWNER),
    MINECART_MOB_SPAWNER(Constants.EntityTypes.Ids.MINECART_SPAWNER, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.MINECART_SPAWNER), EntityType.MINECART_MOB_SPAWNER),
    CREEPER(Constants.EntityTypes.Ids.ENTITY_CREEPER, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_CREEPER), EntityType.CREEPER),
    SKELETON(Constants.EntityTypes.Ids.ENTITY_SKELETON, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_SKELETON), EntityType.SKELETON),
    SPIDER(Constants.EntityTypes.Ids.ENTITY_SPIDER, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_SPIDER), EntityType.SPIDER),
    GIANT(Constants.EntityTypes.Ids.ENTITY_GIANT, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_GIANT), EntityType.GIANT),
    ZOMBIE(Constants.EntityTypes.Ids.ENTITY_ZOMBIE, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_ZOMBIE), EntityType.ZOMBIE),
    SLIME(Constants.EntityTypes.Ids.ENTITY_SLIME, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_SLIME), EntityType.SLIME),
    GHAST(Constants.EntityTypes.Ids.ENTITY_GHAST, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_GHAST), EntityType.GHAST),
    PIG_ZOMBIE(Constants.EntityTypes.Ids.ENTITY_PIG_ZOMBIE, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_PIG_ZOMBIE), EntityType.PIG_ZOMBIE),
    ENDERMAN(Constants.EntityTypes.Ids.ENTITY_ENDERMAN, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_ENDERMAN), EntityType.ENDERMAN),
    CAVE_SPIDER(Constants.EntityTypes.Ids.ENTITY_CAVE_SPIDER, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTTY_CAVE_SPIDER), EntityType.CAVE_SPIDER),
    SILVERFISH(Constants.EntityTypes.Ids.ENTITY_SILVERFISH, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_SILVERFISH), EntityType.SILVERFISH),
    BLAZE(Constants.EntityTypes.Ids.ENTITY_BLAZE, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_BLAZE), EntityType.BLAZE),
    MAGMA_CUBE(Constants.EntityTypes.Ids.ENTITY_LAVA_SLIME, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_LAVA_SLIME), EntityType.MAGMA_CUBE),
    ENDER_DRAGON(Constants.EntityTypes.Ids.ENTITY_ENDERDRAGON, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_ENDERDRAGON), EntityType.ENDER_DRAGON),
    WITHER(Constants.EntityTypes.Ids.ENTITY_WITHER, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_WITHER), EntityType.WITHER),
    BAT(Constants.EntityTypes.Ids.ENTITY_BAT, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_BAT), EntityType.BAT),
    WITCH(Constants.EntityTypes.Ids.ENTITY_WITCH, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_WITCH), EntityType.WITCH),
    PIG(Constants.EntityTypes.Ids.ENTITY_PIG, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_PIG), EntityType.PIG),
    SHEEP(Constants.EntityTypes.Ids.ENTITY_SHEEP, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_SHEEP), EntityType.SHEEP),
    COW(Constants.EntityTypes.Ids.ENTITY_COW, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_COW), EntityType.COW),
    CHICKEN(Constants.EntityTypes.Ids.ENTITY_CHICKEN, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_CHICKEN), EntityType.CHICKEN),
    SQUID(Constants.EntityTypes.Ids.ENTITY_SQUID, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_SQUID), EntityType.SQUID),
    WOLF(Constants.EntityTypes.Ids.ENTITY_WOLF, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_WOLF), EntityType.WOLF),
    MUSHROOM_COW(Constants.EntityTypes.Ids.ENTITY_MUSHROOM_COW, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_MUSHROOM_COW), EntityType.MUSHROOM_COW),
    SNOWMAN(Constants.EntityTypes.Ids.ENTITY_SNOWMAN, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_SNOWMAN), EntityType.SNOWMAN),
    OCELOT(Constants.EntityTypes.Ids.ENTITY_OZELOT, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_OZELOT), EntityType.OCELOT),
    IRON_GOLEM(Constants.EntityTypes.Ids.ENTITY_IRON_GOLEM, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_IRON_GOLEM), EntityType.IRON_GOLEM),
    HORSE(Constants.EntityTypes.Ids.ENTITY_HORSE, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_HORSE), EntityType.HORSE),
    VILLAGER(Constants.EntityTypes.Ids.ENTITY_VILLAGER, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_VILLAGER), EntityType.VILLAGER),
    ENDER_CRYSTAL(Constants.EntityTypes.Ids.ENTITY_ENDERCRYSTAL, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_ENDERCRYSTAL), EntityType.ENDER_CRYSTAL),
    SPLASH_POTION(Constants.EntityTypes.Ids.THROWN_POTION, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.THROWN_POTION), EntityType.SPLASH_POTION),
    EGG(-1, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_EGG), EntityType.EGG),
    FISHING_HOOK(-1, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENITY_FISHING_HOOK), EntityType.FISHING_HOOK),
    LIGHTNING(-1, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_LIGHTNING), EntityType.LIGHTNING),
    WEATHER(-1, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_WEATHER), EntityType.WEATHER),
    PLAYER(-1, NMSClassTemplate.create("EntityPlayer"), EntityType.PLAYER),
    COMPLEX_PART(-1, NMSClassTemplate.create(Constants.EntityTypes.ClassNames.ENTITY_COMPLEX_PART), EntityType.COMPLEX_PART),
    UNKNOWN(-1, NMSClassTemplate.create("Entity"), EntityType.UNKNOWN);

    private final int id;
    private final ClassTemplate TEMPLATE;
    private final EntityType bukkitType;

    private NMSEntityType(int id, ClassTemplate template, EntityType bukkitType) {
        this.id = id;
        this.TEMPLATE = template;
        this.bukkitType = bukkitType;
    }

    public int getEntityId() {
        return this.id;
    }

    public ClassTemplate getClassAsTemplate() {
        return this.TEMPLATE;
    }

    public Class<?> getMobClass() {
        return this.TEMPLATE.getType();
    }

    public EntityType asBukkitEntity() {
        return this.bukkitType;
    }

    public static NMSEntityType getById(int id) {
        for (NMSEntityType type : values()) {
            if (type.getEntityId() == id) {
                return type;
            }
        }
        return null;
    }

    public static NMSEntityType getByClass(Class<?> clazz) {
        for (NMSEntityType type : values()) {
            if (type.getMobClass().isAssignableFrom(clazz)) {
                return type;
            }
        }
        return null;
    }

    public static NMSEntityType fromBukkitType(EntityType type) {
        for (NMSEntityType nmsType : values()) {
            if (nmsType.asBukkitEntity().equals(type)) {
                return nmsType;
            }
        }
        return null;
    }
}
