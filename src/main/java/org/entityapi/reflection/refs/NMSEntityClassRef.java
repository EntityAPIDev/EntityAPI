package org.entityapi.reflection.refs;

import org.bukkit.entity.*;
import org.entityapi.EntityAPICore;

import java.util.HashMap;
import java.util.Map;

public class NMSEntityClassRef {

    private static HashMap<Class<? extends org.bukkit.entity.Entity>, Class<? extends net.minecraft.server.v1_7_R1.Entity>> CLASS_MAP = new HashMap<>();

    static {
        CLASS_MAP.put(org.bukkit.entity.Entity.class, EntityAPICore.SERVER.getNMSClass("Entity"));
        CLASS_MAP.put(Ageable.class, EntityAPICore.SERVER.getNMSClass("EntityAgeable"));
        CLASS_MAP.put(Animals.class, EntityAPICore.SERVER.getNMSClass("EntityAnimal"));
        CLASS_MAP.put(Creature.class, EntityAPICore.SERVER.getNMSClass("EntityCreature"));
        CLASS_MAP.put(Flying.class, EntityAPICore.SERVER.getNMSClass("EntityFlying"));
        CLASS_MAP.put(LivingEntity.class, EntityAPICore.SERVER.getNMSClass("EntityLiving"));
        CLASS_MAP.put(Monster.class, EntityAPICore.SERVER.getNMSClass("EntityMonster"));

        CLASS_MAP.put(Bat.class, EntityAPICore.SERVER.getNMSClass("EntityBat"));
        CLASS_MAP.put(Blaze.class, EntityAPICore.SERVER.getNMSClass("EntityBlaze"));
        CLASS_MAP.put(CaveSpider.class, EntityAPICore.SERVER.getNMSClass("EntityCaveSpider"));
        CLASS_MAP.put(Chicken.class, EntityAPICore.SERVER.getNMSClass("EntityChicken"));
        CLASS_MAP.put(Cow.class, EntityAPICore.SERVER.getNMSClass("EntityCow"));
        CLASS_MAP.put(Creeper.class, EntityAPICore.SERVER.getNMSClass("EntityCreeper"));
        CLASS_MAP.put(EnderDragon.class, EntityAPICore.SERVER.getNMSClass("EntityEnderDragon"));
        CLASS_MAP.put(Enderman.class, EntityAPICore.SERVER.getNMSClass("EntityEnderman"));
        CLASS_MAP.put(Ghast.class, EntityAPICore.SERVER.getNMSClass("EntityGhast"));
        CLASS_MAP.put(Giant.class, EntityAPICore.SERVER.getNMSClass("EntityGiant"));
        CLASS_MAP.put(Golem.class, EntityAPICore.SERVER.getNMSClass("EntityGolem"));
        CLASS_MAP.put(Horse.class, EntityAPICore.SERVER.getNMSClass("EntityHorse"));
        CLASS_MAP.put(HumanEntity.class, EntityAPICore.SERVER.getNMSClass("EntityHuman"));
        CLASS_MAP.put(IronGolem.class, EntityAPICore.SERVER.getNMSClass("EntityIronGolem"));
        CLASS_MAP.put(MagmaCube.class, EntityAPICore.SERVER.getNMSClass("EntityMagmaCube"));
        CLASS_MAP.put(MushroomCow.class, EntityAPICore.SERVER.getNMSClass("EntityMushroomCow"));
        CLASS_MAP.put(Ocelot.class, EntityAPICore.SERVER.getNMSClass("EntityOcelot"));
        CLASS_MAP.put(Pig.class, EntityAPICore.SERVER.getNMSClass("EntityPig"));
        CLASS_MAP.put(PigZombie.class, EntityAPICore.SERVER.getNMSClass("EntityPigZombie"));
        CLASS_MAP.put(Player.class, EntityAPICore.SERVER.getNMSClass("EntityPlayer"));
        CLASS_MAP.put(Sheep.class, EntityAPICore.SERVER.getNMSClass("EntitySheep"));
        CLASS_MAP.put(Silverfish.class, EntityAPICore.SERVER.getNMSClass("EntitySilverfish"));
        CLASS_MAP.put(Skeleton.class, EntityAPICore.SERVER.getNMSClass("EntitySkeleton"));
        CLASS_MAP.put(Slime.class, EntityAPICore.SERVER.getNMSClass("EntitySlime"));
        CLASS_MAP.put(Snowman.class, EntityAPICore.SERVER.getNMSClass("EntitySnowman"));
        CLASS_MAP.put(Spider.class, EntityAPICore.SERVER.getNMSClass("EntitySpider"));
        CLASS_MAP.put(Squid.class, EntityAPICore.SERVER.getNMSClass("EntitySquid"));
        CLASS_MAP.put(Villager.class, EntityAPICore.SERVER.getNMSClass("EntityVillager"));
        CLASS_MAP.put(WaterMob.class, EntityAPICore.SERVER.getNMSClass("EntityWaterAnimal"));
        CLASS_MAP.put(Witch.class, EntityAPICore.SERVER.getNMSClass("EntityWitch"));
        CLASS_MAP.put(Wither.class, EntityAPICore.SERVER.getNMSClass("EntityWither"));
        CLASS_MAP.put(Wolf.class, EntityAPICore.SERVER.getNMSClass("EntityWolf"));
        CLASS_MAP.put(Zombie.class, EntityAPICore.SERVER.getNMSClass("EntityZombie"));
    }

    public static Class<? extends net.minecraft.server.v1_7_R1.Entity> getNMSClass(Class<? extends org.bukkit.entity.Entity> bukkitClass) {
        Class result = null;
        Class superClass = bukkitClass;
        while (result == null && superClass != Object.class) {
            result = CLASS_MAP.get(bukkitClass);
            superClass = superClass.getSuperclass();
        }
        return result;
    }

    public static Class<? extends org.bukkit.entity.Entity> getBukkitClass(Class<? extends net.minecraft.server.v1_7_R1.Entity> nmsClass) {
        Class result = null;
        Class superClass = nmsClass;
        while (result == null && superClass != Object.class) {
            for (Map.Entry<Class<? extends org.bukkit.entity.Entity>, Class<? extends net.minecraft.server.v1_7_R1.Entity>> entry : CLASS_MAP.entrySet()) {
                if (entry.getValue() == nmsClass) {
                    result = entry.getKey();
                }
            }
            superClass = superClass.getSuperclass();
        }
        return result;
    }
}