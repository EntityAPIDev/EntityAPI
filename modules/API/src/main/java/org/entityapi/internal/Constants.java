/*
 * Copyright (C) EntityAPI Team
 *
 * This file is part of EntityAPI.
 *
 * EntityAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EntityAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EntityAPI.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.entityapi.internal;

import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.reflection.Reflection;

/**
 * A class which contains several field names etc.
 * This class is used to identify the fields in NMS classes
 * When a field changes we just have to edit this class and not
 * all classes which access that field/method/class/...
 * <p/>
 * CaptainBern
 * <p/>
 * Enum-ised, but probably not the best way of doing it :\
 * - DSH
 */

public class Constants {

    public static enum EntityTypes {

        CLASS_NAME("EntityTypes"),

        METHOD_REGISTER_ENTITY("a"), //takes class, name and id as param
        METHOD_GET_ID_OF_ENTITY("a"), //takes an entity as param, returns the id
        METHOD_GET_CLASS_OF_ID("a"), //takes id as param, returns the entity class
        METHOD_GET_NAME_OF_ENTITY("b"), //takes entity as param, returns the name
        METHOD_NAME_OF_ID("b"), //takes id as param, returns the name
        METHOD_GET_NAME_MAP("b"), //returns a set which contains all entity names

        MAP_NAME_TO_CLASS("c"),
        MAP_CLASS_TO_NAME("d"),
        MAP_ID_TO_CLASS("e"),
        MAP_CLASS_TO_ID("f"),
        MAP_NAME_TO_ID("g");

        private String v1_7_R1;

        EntityTypes(String v1_7_R1) {
            this.v1_7_R1 = v1_7_R1;
        }

        public String get() {
            return new Reflection().reflect(this.getClass()).getSafeFieldByNameAndType(MinecraftReflection.getVersionTag(), String.class).getAccessor().get(this);
        }

        public static enum Names {
            ITEM("Item"),
            XP_ORB("XPOrb"),
            LEASH("LeashKnot"),
            PANTING("Painting"),
            ARROW("Arrow"),
            SNOWBALL("Snowball"),
            FIREBALL("Fireball"),
            SMALL_FIREBALL("SmallFireball"),
            THROWN_ENDERPEARL("ThrownEnderpearl"),
            EYE_OF_ENDER_SIGNAL("EyeOfEnderSignal"),
            THROWN_POTION("ThrownPotion"),
            THROWN_XP_BOTTLE("ThrownExpBottle"),
            ITEMFRAME("ItemFrame"),
            WITHER_SKULL("WitherSkull"),
            PRIMED_TNT("PrimedTnt"),
            FALLING_SAND("FallingSand"),
            FIREWORKS_ROCKET("FireworksRocketEntity"),
            BOAT("Boat"),
            MINECART_RIDEABLE("MinecartRideable"),
            MINECART_CHEST("MinecartChest"),
            MINECART_FURNANCE("MinecartFurnance"),
            MINECART_TNT("MinecartTNT"),
            MINECART_HOPPER("MinecartHopper"),
            MINECART_SPAWNER("MinecartSpawner"),
            MINECART_COMMANDBLOCK("MinecartCommandBlock"),
            ENTITY_INSENTIENT("Mob"),
            ENTITY_MONSTER("Monster"),
            ENTITY_CREEPER("Creeper"),
            ENTITY_SKELETON("Skeleton"),
            ENTITY_SPIDER("Spider"),
            ENTITY_GIANT("Giant"),
            ENTITY_ZOMBIE("Zombie"),
            ENTITY_SLIME("Slime"),
            ENTITY_GHAST("Ghast"),
            ENTITY_PIG_ZOMBIE("PigZombie"),
            ENTITY_ENDERMAN("Enderman"),
            ENTITY_CAVE_SPIDER("CaveSpider"),
            ENTITY_SILVERFISH("Silverfish"),
            ENTITY_BLAZE("Blaze"),
            ENTITY_LAVA_SLIME("LavaSlime"),
            ENTITY_ENDERDRAGON("EnderDragon"),
            ENTITY_WITHER("WitherBoss"),
            ENTITY_BAT("Bat"),
            ENTITY_WITCH("Witch"),
            ENTITY_PIG("Pig"),
            ENTITY_SHEEP("Sheep"),
            ENTITY_COW("Cow"),
            ENTITY_CHICKEN("Chicken"),
            ENTITY_SQUID("Squid"),
            ENTITY_WOLF("Wolf"),
            ENTITY_MUSHROOM_COW("MushroomCow"),
            ENTITY_SNOWMAN("SnowMan"),
            ENTITY_OZELOT("Ozelot"),
            ENTITY_IRON_GOLEM("VillagerGolem"),
            ENTITY_HORSE("EntityHorse"),
            ENTITY_VILLAGER("Villager"),
            ENTITY_ENDERCRYSTAL("EnderCrystal");

            private String value;

            Names(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }
        }

        @Deprecated
        public static enum Ids {
            ITEM(1),
            XP_ORB(2),
            LEASH(8),
            PANTING(9),
            ARROW(10),
            SNOWBALL(11),
            FIREBALL(12),
            SMALL_FIREBALL(13),
            THROWN_ENDERPEARL(14),
            EYE_OF_ENDER_SIGNAL(15),
            THROWN_POTION(16),
            THROWN_XP_BOTTLE(17),
            ITEMFRAME(18),
            WITHER_SKULL(19),
            PRIMED_TNT(20),
            FALLING_SAND(21),
            FIREWORKS_ROCKET(22),
            BOAT(41),
            MINECART_RIDEABLE(42),
            MINECART_CHEST(43),
            MINECART_FURNANCE(44),
            MINECART_TNT(45),
            MINECART_HOPPER(46),
            MINECART_SPAWNER(47),
            MINECART_COMMANDBLOCK(40),
            ENTITY_INSENTIENT(48),
            ENTITY_MONSTER(49),
            ENTITY_CREEPER(50),
            ENTITY_SKELETON(51),
            ENTITY_SPIDER(52),
            ENTITY_GIANT(53),
            ENTITY_ZOMBIE(54),
            ENTITY_SLIME(55),
            ENTITY_GHAST(56),
            ENTITY_PIG_ZOMBIE(57),
            ENTITY_ENDERMAN(58),
            ENTITY_CAVE_SPIDER(59),
            ENTITY_SILVERFISH(60),
            ENTITY_BLAZE(61),
            ENTITY_LAVA_SLIME(62),
            ENTITY_ENDERDRAGON(63),
            ENTITY_WITHER(64),
            ENTITY_BAT(65),
            ENTITY_WITCH(66),
            ENTITY_PIG(90),
            ENTITY_SHEEP(91),
            ENTITY_COW(92),
            ENTITY_CHICKEN(93),
            ENTITY_SQUID(94),
            ENTITY_WOLF(95),
            ENTITY_MUSHROOM_COW(96),
            ENTITY_SNOWMAN(97),
            ENTITY_OZELOT(98),
            ENTITY_IRON_GOLEM(99),
            ENTITY_HORSE(100),
            ENTITY_VILLAGER(120),
            ENTITY_ENDERCRYSTAL(200);

            private int value;

            Ids(int value) {
                this.value = value;
            }

            public int getValue() {
                return value;
            }
        }

        public static enum ClassNames {
            ITEM("EntityItem"),
            XP_ORB("EntityExperienceOrb"),
            LEASH("EntityLeash"),
            PANTING("EntityPainting"),
            ARROW("EntityArrow"),
            SNOWBALL("EntitySnowball"),
            FIREBALL("EntityLargeFireball"),
            SMALL_FIREBALL("EntitySmallFireball"),
            THROWN_ENDERPEARL("EntityEnderPearl"),
            EYE_OF_ENDER_SIGNAL("EntityEnderSignal"),
            THROWN_POTION("EntityPotion"),
            THROWN_XP_BOTTLE("EntityThrownExpBottle"),
            ITEMFRAME("EntityItemFrame"),
            WITHER_SKULL("EntityWitherSkull"),
            PRIMED_TNT("EntityTNTPrimed"),
            FALLING_SAND("EntityFallingBlock"),
            FIREWORKS_ROCKET("EntityFireworks"),
            BOAT("EntityBoat"),
            MINECART_RIDEABLE("EntityMinecartRideable"),
            MINECART_CHEST("EntityMinecartChest"),
            MINECART_FURNANCE("EntityMinecartFurnace"),
            MINECART_TNT("EntityMinecartTNT"),
            MINECART_HOPPER("EntityMinecartHopper"),
            MINECART_SPAWNER("EntityMinecartFurnace"),
            MINECART_COMMANDBLOCK("EntityMinecartCommandBlock"),
            ENTITY_INSENTIENT("EntityInsentient"),
            ENTITY_MONSTER("IMonster"),
            ENTITY_CREEPER("EntityCreeper"),
            ENTITY_SKELETON("EntitySkeleton"),
            ENTITY_SPIDER("EntitySpider"),
            ENTITY_GIANT("EntityGiantZombie"),
            ENTITY_ZOMBIE("EntityZombie"),
            ENTITY_SLIME("EntitySlime"),
            ENTITY_GHAST("EntityGhast"),
            ENTITY_PIG_ZOMBIE("EntityPigZombie"),
            ENTITY_ENDERMAN("EntityEnderman"),
            ENTTY_CAVE_SPIDER("EntityCaveSpider"),
            ENTITY_SILVERFISH("EntitySilverfish"),
            ENTITY_BLAZE("EntityBlaze"),
            ENTITY_LAVA_SLIME("EntityMagmaCube"),
            ENTITY_ENDERDRAGON("EntityEnderDragon"),
            ENTITY_WITHER("EntityWither"),
            ENTITY_BAT("EntityBat"),
            ENTITY_WITCH("EntityWitch"),
            ENTITY_PIG("EntityPig"),
            ENTITY_SHEEP("EntitySheep"),
            ENTITY_COW("EntityCow"),
            ENTITY_CHICKEN("EntityChicken"),
            ENTITY_SQUID("EntitySquid"),
            ENTITY_WOLF("EntityWolf"),
            ENTITY_MUSHROOM_COW("EntityMushroomCow"),
            ENTITY_SNOWMAN("EntitySnowman"),
            ENTITY_OZELOT("EntityOcelot"),
            ENTITY_IRON_GOLEM("EntityGolem"),
            ENTITY_HORSE("EntityHorse"),
            ENTITY_VILLAGER("EntityVillager"),
            ENTITY_ENDERCRYSTAL("EntityEnderCrystal"),
            ENTITY_EGG("EntityEgg"),
            ENITY_FISHING_HOOK("EntityFishingHook"),
            ENTITY_LIGHTNING("EntityLightning"),
            ENTITY_WEATHER("EntityWeather"),
            ENTITY_COMPLEX_PART("EntityComplexPart");

            private String value;

            ClassNames(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }
        }
    }

    public static enum Entity {

        JUMP("bd");

        private String v1_7_R1;

        Entity(String v1_7_R1) {
            this.v1_7_R1 = v1_7_R1;
        }

        public String get() {
            return new Reflection().reflect(this.getClass()).getSafeFieldByNameAndType(MinecraftReflection.getVersionTag(), String.class).getAccessor().get(this);
        }
    }

    public static enum Server {

        CRAFBUKKIT_ROOT("org.bukkit.craftbukkit"),
        MINECRAFT_ROOT("net.minecraft.server");

        private String value;

        Server(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static enum NetworkManager {

        CHANNEL_FIELD("k"),
        ADDRESS_FIELD("l");

        private String v1_7_R1;

        NetworkManager(String v1_7_R1) {
            this.v1_7_R1 = v1_7_R1;
        }

        public String get() {
            return new Reflection().reflect(this.getClass()).getSafeFieldByNameAndType(MinecraftReflection.getVersionTag(), String.class).getAccessor().get(this);
        }
    }

    public static enum PathfinderGoalSelector {

        CLASS_NAME("PathfinderGoalSelector"),

        METHOD_ADD_GOAL("a"), //takes int and nms.PathfinderGoal as params

        LIST_GOALS("b"),
        LIST_ACTIVE_GOALS("c");

        private String v1_7_R1;

        PathfinderGoalSelector(String v1_7_R1) {
            this.v1_7_R1 = v1_7_R1;
        }

        public String get() {
            return new Reflection().reflect(this.getClass()).getSafeFieldByNameAndType(MinecraftReflection.getVersionTag(), String.class).getAccessor().get(this);
        }
    }
}