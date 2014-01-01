package io.snw.entityapi.entity;

import io.snw.entityapi.api.ControllableEntity;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import io.snw.entityapi.api.mind.Mind;
import io.snw.entityapi.utils.IDGenerator;
import net.minecraft.server.v1_7_R1.EntityLiving;
import net.minecraft.server.v1_7_R1.PathfinderGoalSelector;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the basic controllable entity
 *
 * @param <T> Bukkit entity that this is based off
 */
public class ControllableBaseEntity<T extends LivingEntity> implements ControllableEntity {

    protected long id;
    protected Mind mind;
    protected boolean updateAttributes;

    protected boolean canFly;

    protected EntityLiving handle;
    protected ControllableEntityType entityType;
    protected HashMap<EntitySound, Map<String, String>> sounds = new HashMap<>();

    public ControllableBaseEntity(ControllableEntityType entityType) {
        this.id = IDGenerator.getNextId();
        this.mind = new Mind(this);
        this.entityType = entityType;
        this.initSounds();
    }

    public long getId() {
        return id;
    }

    public void initSounds() {
    }

    @Override
    public boolean canFly() {
        return this.canFly;
    }

    @Override
    public void setCanFly(boolean flag) {
        this.canFly = flag;
    }

    public Mind getMind() {
        return mind;
    }

    public boolean shouldUpdateAttributes() {
        return updateAttributes;
    }

    public void setUpdateAttributes(boolean flag) {
        this.updateAttributes = flag;
    }

    @Override
    public LivingEntity getBukkitEntity() {
        return (T) this.handle.getBukkitEntity();
    }

    public EntityLiving getHandle() {
        return handle;
    }

    @Override
    public ControllableEntityType getEntityType() {
        return this.entityType;
    }

    public Map<String, String> getSounds(EntitySound type) {
        return this.sounds.get(type);
    }

    public String getSound(EntitySound type) {
        return this.getSound(type, "default");
    }

    public String getSound(EntitySound type, String key) {
        for (Map.Entry<String, String> entry : this.sounds.get(type).entrySet()) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void setSound(EntitySound type, String sound) {
        this.setSound(type, "default", sound);
    }

    public void setSound(EntitySound type, String key, String sound) {
        if (this.sounds.containsKey(type)) {
            Map<String, String> map = this.sounds.get(type);
            map.put(key, sound);
            this.sounds.put(type, map);
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put(key, sound);
            this.sounds.put(type, map);
        }
    }

    public void setSound(EntitySound type, HashMap<String, String> soundMap) {
        this.sounds.put(type, soundMap);
    }

    protected void clearNMSGoals(PathfinderGoalSelector[] selectors) {
        // We have our own AI (Mind), so these aren't needed.
        try {
            String[] fieldNames = new String[]{"b", "c"};
            for (PathfinderGoalSelector selector : selectors) {
                for (String s : fieldNames) {
                    Field f = PathfinderGoalSelector.class.getDeclaredField(s);
                    f.setAccessible(true);
                    ((List) f.get(selector)).clear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTick() {
    }

    @Override
    public boolean onInteract(Player entity, boolean rightClick) {
        return false;
    }

    @Override
    public Vector onPush(float x, float y, float z) {
        return null;
    }

    @Override
    public boolean onCollide(Entity entity) {
        return false;
    }

    @Override
    public void onDeath() {

    }
}