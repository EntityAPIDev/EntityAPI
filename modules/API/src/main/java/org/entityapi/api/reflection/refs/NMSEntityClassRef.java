package org.entityapi.api.reflection.refs;

import org.bukkit.entity.*;
import org.entityapi.api.plugin.EntityAPI;

import java.util.HashMap;
import java.util.Map;

public class NMSEntityClassRef {

    private static HashMap<Class<? extends org.bukkit.entity.Entity>, Class<?>> CLASS_MAP = new HashMap<>();

    static {
        CLASS_MAP.put(org.bukkit.entity.Entity.class, EntityAPI.getCore().getAPIServer().getNMSClass("Entity"));
        CLASS_MAP.put(Ageable.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityAgeable"));
        CLASS_MAP.put(Animals.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityAnimal"));
        CLASS_MAP.put(Creature.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityCreature"));
        CLASS_MAP.put(Flying.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityFlying"));
        CLASS_MAP.put(LivingEntity.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityLiving"));
        CLASS_MAP.put(Monster.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityMonster"));

        CLASS_MAP.put(Bat.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityBat"));
        CLASS_MAP.put(Blaze.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityBlaze"));
        CLASS_MAP.put(CaveSpider.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityCaveSpider"));
        CLASS_MAP.put(Chicken.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityChicken"));
        CLASS_MAP.put(Cow.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityCow"));
        CLASS_MAP.put(Creeper.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityCreeper"));
        CLASS_MAP.put(EnderDragon.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityEnderDragon"));
        CLASS_MAP.put(Enderman.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityEnderman"));
        CLASS_MAP.put(Ghast.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityGhast"));
        CLASS_MAP.put(Giant.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityGiant"));
        CLASS_MAP.put(Golem.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityGolem"));
        CLASS_MAP.put(Horse.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityHorse"));
        CLASS_MAP.put(HumanEntity.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityHuman"));
        CLASS_MAP.put(IronGolem.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityIronGolem"));
        CLASS_MAP.put(MagmaCube.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityMagmaCube"));
        CLASS_MAP.put(MushroomCow.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityMushroomCow"));
        CLASS_MAP.put(Ocelot.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityOcelot"));
        CLASS_MAP.put(Pig.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityPig"));
        CLASS_MAP.put(PigZombie.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityPigZombie"));
        CLASS_MAP.put(Player.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityPlayer"));
        CLASS_MAP.put(Sheep.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntitySheep"));
        CLASS_MAP.put(Silverfish.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntitySilverfish"));
        CLASS_MAP.put(Skeleton.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntitySkeleton"));
        CLASS_MAP.put(Slime.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntitySlime"));
        CLASS_MAP.put(Snowman.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntitySnowman"));
        CLASS_MAP.put(Spider.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntitySpider"));
        CLASS_MAP.put(Squid.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntitySquid"));
        CLASS_MAP.put(Villager.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityVillager"));
        CLASS_MAP.put(WaterMob.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityWaterAnimal"));
        CLASS_MAP.put(Witch.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityWitch"));
        CLASS_MAP.put(Wither.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityWither"));
        CLASS_MAP.put(Wolf.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityWolf"));
        CLASS_MAP.put(Zombie.class, EntityAPI.getCore().getAPIServer().getNMSClass("EntityZombie"));
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
            for (Map.Entry<Class<? extends org.bukkit.entity.Entity>, Class<?>> entry : CLASS_MAP.entrySet()) {
                if (entry.getValue() == nmsClass) {
                    result = entry.getKey();
                }
            }
            superClass = superClass.getSuperclass();
        }
        return result;
    }
}