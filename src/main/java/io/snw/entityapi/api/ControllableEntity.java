package io.snw.entityapi.api;

import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import io.snw.entityapi.api.mind.Mind;
import net.minecraft.server.v1_7_R1.EntityLiving;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents our controllable entity
 */

public abstract interface ControllableEntity {

    public abstract long getId();

    public Mind getMind();

    public boolean shouldUpdateAttributes();

    public void setUpdateAttributes(boolean flag);

    public boolean canFly();

    public void setCanFly(boolean flag);

    public abstract LivingEntity getBukkitEntity();

    public abstract EntityLiving getHandle();

    public abstract ControllableEntityType getEntityType();

    public Map<String, String> getSounds(EntitySound type);

    public String getSound(EntitySound type);

    public String getSound(EntitySound type, String key);

    public void setSound(EntitySound type, String sound);

    public void setSound(EntitySound type, String key, String sound);

    public void setSound(EntitySound type, HashMap<String, String> soundMap);

    public void onTick();

    public boolean onInteract(Player entity, boolean rightClick);

    public Vector onPush(float x, float y, float z);

    public boolean onCollide(Entity entity);

    public void onDeath();
}
