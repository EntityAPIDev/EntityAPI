package org.entityapi.api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.entityapi.api.mind.Mind;

import java.util.HashMap;
import java.util.Map;

public abstract interface ControllableEntity<T extends LivingEntity> extends Nameable, Attacking {

    // TODO: JavaDucks. Quack

    public EntityManager getEntityManager();

    public abstract int getId();

    public Mind getMind();

    public abstract T getBukkitEntity();

    public abstract ControllableEntityType getEntityType();

    public float getHeight();

    public float getWidth();

    public boolean spawnEntity(Location spawnLocation);

    public boolean hasSpawned();

    @Override
    public String getName();

    @Override
    public boolean setName(String name);

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

    public void setDefaultBehaviours();
}