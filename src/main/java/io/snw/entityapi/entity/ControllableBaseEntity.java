package io.snw.entityapi.entity;

import io.snw.entityapi.api.ControllableEntity;
import io.snw.entityapi.api.ControllableEntityType;
import io.snw.entityapi.api.EntitySound;
import io.snw.entityapi.api.mind.Mind;
import io.snw.entityapi.utils.IDGenerator;
import net.minecraft.server.v1_7_R1.*;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R1.CraftSound;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
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
    protected boolean tickAttributes;

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

    public Mind getMind() {
        return mind;
    }

    @Override
    public String getName() {
        if (this.handle == null) {
            return null; // TODO: Give them a default name?
        }
        return this.getBukkitEntity().getCustomName();
    }

    @Override
    public void setName(String name) {
        if (this.handle == null) {
            // TODO: set default name here?
        }
        this.getBukkitEntity().setCustomName(name);
        this.getBukkitEntity().setCustomNameVisible(name == null ? false : true);
    }

    @Override
    public boolean canFly() {
        return this.canFly;
    }

    @Override
    public void setCanFly(boolean flag) {
        this.canFly = flag;
    }

    public boolean shouldUpdateAttributes() {
        return tickAttributes;
    }

    public void setTickAttributes(boolean flag) {
        this.tickAttributes = flag;
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
        String s = this.getSound(type, "custom");
        if (s != null) {
            return s;
        }
        return this.getSound(type, "default");
    }

    public String getSound(EntitySound type, String key) {
        String s = this.getSound(type, "custom");
        if (s != null) {
            return s;
        }
        for (Map.Entry<String, String> entry : this.sounds.get(type).entrySet()) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        // Minecraft will treat this as 'no sound'
        return "";
    }

    @Override
    public void setSound(EntitySound type, Sound toReplace, Sound replaceWith, boolean addOnFail) {
        if (toReplace == null) {
            throw new IllegalArgumentException("Sound to replace cannot be null");
        }
        boolean removed = false;
        String newKey = "custom";
        Iterator<Map.Entry<String, String>> i = this.sounds.get(type).entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry<String, String> entry = i.next();
            if (entry.getValue().equals(CraftSound.getSound(toReplace))) { // This way, we're doing the NMS stuff
                newKey = entry.getKey();
                removed = true;
                i.remove();
            }
        }

        if (replaceWith != null) { // Allows sounds to be removed
            if (removed || addOnFail) { // On fail - offer the option to add the sound anyway. Functions as setSound(type, sound) too
                this.setSound(type, newKey, CraftSound.getSound(replaceWith));
            }
        }
    }

    @Override
    public void setSound(EntitySound type, Sound sound) {
        // Allows sounds to be set without the use of the NMS String
        // We can also allow people to add/replace/remove sounds
        // Entities use the "custom" sound if one exists
        this.setSound(type, "custom", CraftSound.getSound(sound));
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

    public double getSpeed() {
        if (this.handle == null) {
            return GenericAttributes.d.b();
        }
        return this.handle.getAttributeInstance(GenericAttributes.d).getValue();
    }

    public void setSpeed(double speed) {
        this.handle.getAttributeInstance(GenericAttributes.d).setValue(speed);
    }

    public void setPathfindingRange(double range) {
        this.handle.getAttributeInstance(GenericAttributes.b).setValue(range);
    }

    public double getPathfindingRange() {
        return this.handle.getAttributeInstance(GenericAttributes.b).getValue();
    }

    public boolean navigateTo(LivingEntity livingEntity) {
        return this.navigateTo(livingEntity, this.getSpeed());
    }

    public boolean navigateTo(LivingEntity livingEntity, double speed) {
        if (livingEntity == null) {
            return false;
        }
        EntityLiving target = ((CraftLivingEntity) livingEntity).getHandle();
        if (target == this.handle) {
            return true;
        }
        PathEntity path = this.handle.world.findPath(this.handle, ((CraftLivingEntity) livingEntity).getHandle(), (float) this.getPathfindingRange(), true, false, false, true);
        return this.navigateTo(path, speed);
    }

    public boolean navigateTo(Location to, double speed) {
        if (to == null) {
            return false;
        }
        PathEntity path = this.handle.world.a(this.handle, MathHelper.floor(to.getX()), MathHelper.f(to.getY()), MathHelper.floor(to.getZ()), (float) this.getPathfindingRange(), true, false, false, true);
        return this.navigateTo(path, speed);
    }

    public boolean navigateTo(PathEntity path, double speed) {
        if (!(this.handle instanceof EntityInsentient) || path == null) {
            return false;
        }
        return ((EntityInsentient) this.handle).getNavigation().a(path, speed);
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

    public void initSounds() {
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
}