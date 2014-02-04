package io.snw.entityapi.internal;

public class Constants {

    /**
     * A class which contains several field names etc.
     * This class is used to identify the fields in NMS classes
     * When a field changes we just have to edit this class and not
     * all classes which access that field/method/class/...
     * <p/>
     * CaptainBern
     */

    public static class EntityTypes {
        public static final String CLASS_NAME = "EntityTypes";

        public static final String METHOD_REGISTER_ENTITY = "a"; //takes class, name and id as param
        public static final String METHOD_GET_ID_OF_ENTITY = "a"; //takes an entity as param, returns the id
        public static final String METHOD_GET_CLASS_OF_ID = "a"; //takes id as param, returns the entity class
        public static final String METHOD_GET_NAME_OF_ENTITY = "b"; //takes entity as param, returns the name
        public static final String METHOD_NAME_OF_ID = "b"; //takes id as param, returns the name
        public static final String METHOD_GET_NAME_MAP = "b"; //returns a set which contains all entity names

        public static final String MAP_NAME_TO_CLASS = "c";
        public static final String MAP_CLASS_TO_NAME = "d";
        public static final String MAP_ID_TO_CLASS = "e";
        public static final String MAP_CLASS_TO_ID = "f";
        public static final String MAP_NAME_TO_ID = "g";

        public static class Names {
            public static final String ITEM = "Item";
            public static final String XP_ORB = "XPOrb";
            public static final String LEASH = "LeashKnot";
            public static final String PANTING = "Painting";
            public static final String ARROW = "Arrow";
            public static final String SNOWBALL = "Snowball";
            public static final String FIREBALL = "Fireball";
            public static final String SMALL_FIREBALL = "SmallFireball";
            public static final String THROWN_ENDERPEARL = "ThrownEnderpearl";
            public static final String EYE_OF_ENDER_SIGNAL = "EyeOfEnderSignal";
            public static final String THROWN_POTION = "ThrownPotion";
            public static final String THROWN_XP_BOTTLE = "ThrownExpBottle";
            public static final String ITEMFRAME = "ItemFrame";
            public static final String WITHER_SKULL = "WitherSkull";
            public static final String PRIMED_TNT = "PrimedTnt";
            public static final String FALLING_SAND = "FallingSand";
            public static final String FIREWORKS_ROCKET = "FireworksRocketEntity";
            public static final String BOAT = "Boat";
            public static final String MINECART_RIDEABLE = "MinecartRideable";
            public static final String MINECART_CHEST = "MinecartChest";
            public static final String MINECART_FURNANCE = "MinecartFurnance";
            public static final String MINECART_TNT = "MinecartTNT";
            public static final String MINECART_HOPPER = "MinecartHopper";
            public static final String MINECART_SPAWNER = "MinecartSpawner";
            public static final String MINECART_COMMANDBLOCK = "MinecartCommandBlock";
            public static final String ENTITY_INSENTIENT = "Mob";
            public static final String ENTITY_MONSTER = "Monster";
            public static final String ENTITY_CREEPER = "Creeper";
            public static final String ENTITY_SKELETON = "Skeleton";
            public static final String ENTITY_SPIDER = "Spider";
            public static final String ENTITY_GIANT = "Giant";
            public static final String ENTITY_ZOMBIE = "Zombie";
            public static final String ENTITY_SLIME = "Slime";
            public static final String ENTITY_GHAST = "Ghast";
            public static final String ENTITY_PIG_ZOMBIE = "PigZombie";
            public static final String ENTITY_ENDERMAN = "Enderman";
            public static final String ENTITY_CAVE_SPIDER = "CaveSpider";
            public static final String ENTITY_SILVERFISH = "Silverfish";
            public static final String ENTITY_BLAZE = "Blaze";
            public static final String ENTITY_LAVA_SLIME = "LavaSlime";
            public static final String ENTITY_ENDERDRAGON = "EnderDragon";
            public static final String ENTITY_WITHER = "WitherBoss";
            public static final String ENTITY_BAT = "Bat";
            public static final String ENTITY_WITCH = "Witch";
            public static final String ENTITY_PIG = "Pig";
            public static final String ENTITY_SHEEP = "Sheep";
            public static final String ENTITY_COW = "Cow";
            public static final String ENTITY_CHICKEN = "Chicken";
            public static final String ENTITY_SQUID = "Squid";
            public static final String ENTITY_WOLF = "Wolf";
            public static final String ENTITY_MUSHROOM_COW = "MushroomCow";
            public static final String ENTITY_SNOWMAN = "SnowMan";
            public static final String ENTITY_OZELOT = "Ozelot";
            public static final String ENTITY_IRON_GOLEM = "VillagerGolem";
            public static final String ENTITY_HORSE = "EntityHorse";
            public static final String ENTITY_VILLAGER = "Villager";
            public static final String ENTITY_ENDERCRYSTAL = "EnderCrystal";
        }

        @Deprecated
        public static class Ids {
            public static final int ITEM = 1;
            public static final int XP_ORB = 2;
            public static final int LEASH = 8;
            public static final int PANTING = 9;
            public static final int ARROW = 10;
            public static final int SNOWBALL = 11;
            public static final int FIREBALL = 12;
            public static final int SMALL_FIREBALL = 13;
            public static final int THROWN_ENDERPEARL = 14;
            public static final int EYE_OF_ENDER_SIGNAL = 15;
            public static final int THROWN_POTION = 16;
            public static final int THROWN_XP_BOTTLE = 17;
            public static final int ITEMFRAME = 18;
            public static final int WITHER_SKULL = 19;
            public static final int PRIMED_TNT = 20;
            public static final int FALLING_SAND = 21;
            public static final int FIREWORKS_ROCKET = 22;
            public static final int BOAT = 41;
            public static final int MINECART_RIDEABLE = 42;
            public static final int MINECART_CHEST = 43;
            public static final int MINECART_FURNANCE = 44;
            public static final int MINECART_TNT = 45;
            public static final int MINECART_HOPPER = 46;
            public static final int MINECART_SPAWNER = 47;
            public static final int MINECART_COMMANDBLOCK = 40;
            public static final int ENTITY_INSENTIENT = 48;
            public static final int ENTITY_MONSTER = 49;
            public static final int ENTITY_CREEPER = 50;
            public static final int ENTITY_SKELETON = 51;
            public static final int ENTITY_SPIDER = 52;
            public static final int ENTITY_GIANT = 53;
            public static final int ENTITY_ZOMBIE = 54;
            public static final int ENTITY_SLIME = 55;
            public static final int ENTITY_GHAST = 56;
            public static final int ENTITY_PIG_ZOMBIE = 57;
            public static final int ENTITY_ENDERMAN = 58;
            public static final int ENTITY_CAVE_SPIDER = 59;
            public static final int ENTITY_SILVERFISH = 60;
            public static final int ENTITY_BLAZE = 61;
            public static final int ENTITY_LAVA_SLIME = 62;
            public static final int ENTITY_ENDERDRAGON = 63;
            public static final int ENTITY_WITHER = 64;
            public static final int ENTITY_BAT = 65;
            public static final int ENTITY_WITCH = 66;
            public static final int ENTITY_PIG = 90;
            public static final int ENTITY_SHEEP = 91;
            public static final int ENTITY_COW = 92;
            public static final int ENTITY_CHICKEN = 93;
            public static final int ENTITY_SQUID = 94;
            public static final int ENTITY_WOLF = 95;
            public static final int ENTITY_MUSHROOM_COW = 96;
            public static final int ENTITY_SNOWMAN = 97;
            public static final int ENTITY_OZELOT = 98;
            public static final int ENTITY_IRON_GOLEM = 99;
            public static final int ENTITY_HORSE = 100;
            public static final int ENTITY_VILLAGER = 120;
            public static final int ENTITY_ENDERCRYSTAL = 200;
        }

        public static class ClassNames {
            public static final String ITEM = "EntityItem";
            public static final String XP_ORB = "EntityExperienceOrb";
            public static final String LEASH = "EntityLeash";
            public static final String PANTING = "EntityPainting";
            public static final String ARROW = "EntityArrow";
            public static final String SNOWBALL = "EntitySnowball";
            public static final String FIREBALL = "EntityLargeFireball";
            public static final String SMALL_FIREBALL = "EntitySmallFireball";
            public static final String THROWN_ENDERPEARL = "EntityEnderPearl";
            public static final String EYE_OF_ENDER_SIGNAL = "EntityEnderSignal";
            public static final String THROWN_POTION = "EntityPotion";
            public static final String THROWN_XP_BOTTLE = "EntityThrownExpBottle";
            public static final String ITEMFRAME = "EntityItemFrame";
            public static final String WITHER_SKULL = "EntityWitherSkull";
            public static final String PRIMED_TNT = "EntityTNTPrimed";
            public static final String FALLING_SAND = "EntityFallingBlock";
            public static final String FIREWORKS_ROCKET = "EntityFireworks";
            public static final String BOAT = "EntityBoat";
            public static final String MINECART_RIDEABLE = "EntityMinecartRideable";
            public static final String MINECART_CHEST = "EntityMinecartChest";
            public static final String MINECART_FURNANCE = "EntityMinecartFurnace";
            public static final String MINECART_TNT = "EntityMinecartTNT";
            public static final String MINECART_HOPPER = "EntityMinecartHopper";
            public static final String MINECART_SPAWNER = "EntityMinecartFurnace";
            public static final String MINECART_COMMANDBLOCK = "EntityMinecartCommandBlock";
            public static final String ENTITY_INSENTIENT = "EntityInsentient";
            public static final String ENTITY_MONSTER = "IMonster";
            public static final String ENTITY_CREEPER = "EntityCreeper";
            public static final String ENTITY_SKELETON = "EntitySkeleton";
            public static final String ENTITY_SPIDER = "EntitySpider";
            public static final String ENTITY_GIANT = "EntityGiantZombie";
            public static final String ENTITY_ZOMBIE = "EntityZombie";
            public static final String ENTITY_SLIME = "EntitySlime";
            public static final String ENTITY_GHAST = "EntityGhast";
            public static final String ENTITY_PIG_ZOMBIE = "EntityPigZombie";
            public static final String ENTITY_ENDERMAN = "EntityEnderman";
            public static final String ENTTY_CAVE_SPIDER = "EntityCaveSpider";
            public static final String ENTITY_SILVERFISH = "EntitySilverfish";
            public static final String ENTITY_BLAZE = "EntityBlaze";
            public static final String ENTITY_LAVA_SLIME = "EntityMagmaCube";
            public static final String ENTITY_ENDERDRAGON = "EntityEnderDragon";
            public static final String ENTITY_WITHER = "EntityWither";
            public static final String ENTITY_BAT = "EntityBat";
            public static final String ENTITY_WITCH = "EntityWitch";
            public static final String ENTITY_PIG = "EntityPig";
            public static final String ENTITY_SHEEP = "EntitySheep";
            public static final String ENTITY_COW = "EntityCow";
            public static final String ENTITY_CHICKEN = "EntityChicken";
            public static final String ENTITY_SQUID = "EntitySquid";
            public static final String ENTITY_WOLF = "EntityWolf";
            public static final String ENTITY_MUSHROOM_COW = "EntityMushroomCow";
            public static final String ENTITY_SNOWMAN = "EntitySnowman";
            public static final String ENTITY_OZELOT = "EntityOcelot";
            public static final String ENTITY_IRON_GOLEM = "EntityGolem";
            public static final String ENTITY_HORSE = "EntityHorse";
            public static final String ENTITY_VILLAGER = "EntityVillager";
            public static final String ENTITY_ENDERCRYSTAL = "EntityEnderCrystal";
            public static final String ENTITY_EGG = "EntityEgg";
            public static final String ENITY_FISHING_HOOK = "EntityFishingHook";
            public static final String ENTITY_LIGHTNING = "EntityLightning";
            public static final String ENTITY_WEATHER = "EntityWeather";
            public static final String ENTITY_COMPLEX_PART = "EntityComplexPart";
        }
    }

    public static class Server {

        public static final String SUPPORTED_VERSION = "v1_7_R1";
        public static final int SUPPORTED_VERSION_NUMERIC = 171;

        public static final String CRAFBUKKIT_ROOT = "org.bukkit.craftbukkit";
        public static final String MINECRAFT_ROOT = "net.minecraft.server";
    }

    public static class NetworkManager {

        public static final String CHANNEL_FIELD = "k";
        public static final String ADDRESS_FIELD = "l";
    }
}
