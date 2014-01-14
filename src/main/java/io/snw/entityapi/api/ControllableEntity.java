package io.snw.entityapi.api;

import io.snw.entityapi.api.mind.Mind;
import net.minecraft.server.v1_7_R1.EntityLiving;
import net.minecraft.server.v1_7_R1.PathEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents our controllable entity
 */

public abstract interface ControllableEntity extends Nameable {

    // TODO: JavaDucks. Quack

    public abstract long getId();

    public Mind getMind();

    public abstract LivingEntity getBukkitEntity();

    public abstract EntityLiving getHandle();

    public abstract ControllableEntityType getEntityType();

    public Map<String, String> getSounds(EntitySound type);

    public String getSound(EntitySound type);

    public String getSound(EntitySound type, String key);

    public String getCustomSound(EntitySound type, String key);

    public void setSound(EntitySound type, Sound toReplace, Sound replaceWith, boolean addOnFail);

    public void setSound(EntitySound type, Sound sound);

    public void setSound(EntitySound type, Sound sound, String key);

    public void setSound(EntitySound type, String sound);

    public void setSound(EntitySound type, String key, String sound);

    public void setSound(EntitySound type, HashMap<String, String> soundMap);

    public boolean canFly();

    public void setCanFly(boolean flag);

    public Material getLoot();

    public void setLoot(Material material);

    public boolean shouldUpdateAttributes();

    public void setTickAttributes(boolean flag);

    public double getSpeed();

    public void setSpeed(double speed);

    public void setPathfindingRange(double range);

    public double getPathfindingRange();

    public boolean navigateTo(LivingEntity livingEntity);

    public boolean navigateTo(LivingEntity livingEntity, double speed);

    public boolean navigateTo(Location to, double speed);

    public boolean navigateTo(PathEntity path, double speed);

    public void onTick();

    public boolean onInteract(Player entity, boolean rightClick);

    public Vector onPush(float x, float y, float z);

    public boolean onCollide(Entity entity);

    public void onDeath();
}
