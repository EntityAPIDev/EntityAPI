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

    public static class EntityTypes {

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
    }
}