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

package org.entityapi.reflection.refs;

import org.bukkit.entity.*;
import org.entityapi.api.entity.type.bukkit.InsentientEntity;
import org.entityapi.reflection.utility.CommonReflection;

import java.util.HashMap;
import java.util.Map;

public class NMSEntityClassRef {

    private static HashMap<Class<?>, Class<?>> CLASS_MAP = new HashMap<>();

    static {
        CLASS_MAP.put(org.bukkit.entity.Entity.class, CommonReflection.getMinecraftClass("Entity"));
        CLASS_MAP.put(Ageable.class, CommonReflection.getMinecraftClass("EntityAgeable"));
        CLASS_MAP.put(Animals.class, CommonReflection.getMinecraftClass("EntityAnimal"));
        CLASS_MAP.put(Creature.class, CommonReflection.getMinecraftClass("EntityCreature"));
        CLASS_MAP.put(Flying.class, CommonReflection.getMinecraftClass("EntityFlying"));
        CLASS_MAP.put(LivingEntity.class, CommonReflection.getMinecraftClass("EntityLiving"));
        CLASS_MAP.put(Monster.class, CommonReflection.getMinecraftClass("EntityMonster"));

        CLASS_MAP.put(InsentientEntity.class, CommonReflection.getMinecraftClass("EntityInsentient"));

        CLASS_MAP.put(Bat.class, CommonReflection.getMinecraftClass("EntityBat"));
        CLASS_MAP.put(Blaze.class, CommonReflection.getMinecraftClass("EntityBlaze"));
        CLASS_MAP.put(CaveSpider.class, CommonReflection.getMinecraftClass("EntityCaveSpider"));
        CLASS_MAP.put(Chicken.class, CommonReflection.getMinecraftClass("EntityChicken"));
        CLASS_MAP.put(Cow.class, CommonReflection.getMinecraftClass("EntityCow"));
        CLASS_MAP.put(Creeper.class, CommonReflection.getMinecraftClass("EntityCreeper"));
        CLASS_MAP.put(EnderDragon.class, CommonReflection.getMinecraftClass("EntityEnderDragon"));
        CLASS_MAP.put(Enderman.class, CommonReflection.getMinecraftClass("EntityEnderman"));
        CLASS_MAP.put(Ghast.class, CommonReflection.getMinecraftClass("EntityGhast"));
        CLASS_MAP.put(Giant.class, CommonReflection.getMinecraftClass("EntityGiant"));
        CLASS_MAP.put(Golem.class, CommonReflection.getMinecraftClass("EntityGolem"));
        CLASS_MAP.put(Horse.class, CommonReflection.getMinecraftClass("EntityHorse"));
        CLASS_MAP.put(HumanEntity.class, CommonReflection.getMinecraftClass("EntityHuman"));
        CLASS_MAP.put(IronGolem.class, CommonReflection.getMinecraftClass("EntityIronGolem"));
        CLASS_MAP.put(MagmaCube.class, CommonReflection.getMinecraftClass("EntityMagmaCube"));
        CLASS_MAP.put(MushroomCow.class, CommonReflection.getMinecraftClass("EntityMushroomCow"));
        CLASS_MAP.put(Ocelot.class, CommonReflection.getMinecraftClass("EntityOcelot"));
        CLASS_MAP.put(Pig.class, CommonReflection.getMinecraftClass("EntityPig"));
        CLASS_MAP.put(PigZombie.class, CommonReflection.getMinecraftClass("EntityPigZombie"));
        CLASS_MAP.put(Player.class, CommonReflection.getMinecraftClass("EntityPlayer"));
        CLASS_MAP.put(Sheep.class, CommonReflection.getMinecraftClass("EntitySheep"));
        CLASS_MAP.put(Silverfish.class, CommonReflection.getMinecraftClass("EntitySilverfish"));
        CLASS_MAP.put(Skeleton.class, CommonReflection.getMinecraftClass("EntitySkeleton"));
        CLASS_MAP.put(Slime.class, CommonReflection.getMinecraftClass("EntitySlime"));
        CLASS_MAP.put(Snowman.class, CommonReflection.getMinecraftClass("EntitySnowman"));
        CLASS_MAP.put(Spider.class, CommonReflection.getMinecraftClass("EntitySpider"));
        CLASS_MAP.put(Squid.class, CommonReflection.getMinecraftClass("EntitySquid"));
        CLASS_MAP.put(Villager.class, CommonReflection.getMinecraftClass("EntityVillager"));
        CLASS_MAP.put(WaterMob.class, CommonReflection.getMinecraftClass("EntityWaterAnimal"));
        CLASS_MAP.put(Witch.class, CommonReflection.getMinecraftClass("EntityWitch"));
        CLASS_MAP.put(Wither.class, CommonReflection.getMinecraftClass("EntityWither"));
        CLASS_MAP.put(Wolf.class, CommonReflection.getMinecraftClass("EntityWolf"));
        CLASS_MAP.put(Zombie.class, CommonReflection.getMinecraftClass("EntityZombie"));
    }

    public static Class<?> getNMSClass(Class<? extends org.bukkit.entity.Entity> bukkitClass) {
        Class result = null;
        Class superClass = bukkitClass;
        while (result == null && superClass != Object.class) {
            result = CLASS_MAP.get(bukkitClass);
            superClass = superClass.getSuperclass();
        }
        return result;
    }

    public static Class<? extends org.bukkit.entity.Entity> getBukkitClass(Class<?> nmsClass) {
        Class result = null;
        Class superClass = nmsClass;
        while (result == null && superClass != Object.class) {
            for (Map.Entry<Class<?>, Class<?>> entry : CLASS_MAP.entrySet()) {
                if (entry.getValue() == nmsClass) {
                    result = entry.getKey();
                }
            }
            superClass = superClass.getSuperclass();
        }
        return result;
    }
}