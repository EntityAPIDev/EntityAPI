package org.entityapi.entity;

import net.minecraft.server.v1_7_R1.World;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.entityapi.EntityAPICore;
import org.entityapi.EntityManager;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.ControllableEntityHandle;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.events.*;
import org.entityapi.api.mind.Mind;
import net.minecraft.server.v1_7_R1.*;
import org.bukkit.craftbukkit.v1_7_R1.CraftSound;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.entityapi.api.EntitySound;
import org.entityapi.api.mind.behaviour.Behaviour;
import org.entityapi.reflection.SafeConstructor;
import org.entityapi.utils.WorldUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents the basic controllable entity
 *
 * @param <T> Bukkit entity that this is based off
 */
public abstract class ControllableBaseEntity<T extends LivingEntity> implements ControllableEntity<T> {

    protected final EntityManager manager;

    private boolean hasSpawned;
    private final int ID;
    protected Mind mind;
    protected boolean tickAttributes;

    protected boolean canFly;
    protected org.bukkit.Material loot;

    protected EntityLiving handle;
    protected ControllableEntityType entityType;
    protected HashMap<EntitySound, Map<String, String>> sounds = new HashMap<>();

    public ControllableBaseEntity(int id, ControllableEntityType entityType, EntityManager manager) {
        this.manager = manager;
        this.ID = id;
        this.mind = new Mind();
        this.mind.setControllableEntity(this);
        this.entityType = entityType;
        this.initSounds();
    }

    @Override
    public EntityManager getEntityManager() {
        return this.manager;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Mind getMind() {
        return mind;
    }

    @Override
    public T getBukkitEntity() {
        return (T) this.handle.getBukkitEntity();
    }

    @Override
    public EntityLiving getHandle() {
        return handle;
    }

    @Override
    public ControllableEntityType getEntityType() {
        return this.entityType;
    }

    @Override
    public float getHeight() {
        return this.handle.height;
    }

    @Override
    public float getWidth() {
        return this.handle.width;
    }

    @Override
    public boolean spawnEntity(Location spawnLocation) {
        ControllableEntityPreSpawnEvent spawnEvent = new ControllableEntityPreSpawnEvent(this, spawnLocation);
        EntityAPICore.getCore().getServer().getPluginManager().callEvent(spawnEvent);
        if (spawnEvent.isCancelled()) {
            return false;
        }
        Location l = spawnEvent.getSpawnLocation();
        net.minecraft.server.v1_7_R1.World mcWorld = ((CraftWorld) l.getWorld()).getHandle();

        SafeConstructor<ControllableEntityHandle> entityConstructor = new SafeConstructor<>(this.getEntityType().getHandleClass(), World.class, ControllableEntity.class);
        ControllableEntityHandle controllableEntityHandle = entityConstructor.newInstance(WorldUtil.toNMSWorld(l.getWorld()), this);
        if (handle instanceof EntityLiving) {
            EntityLiving handle = (EntityLiving) controllableEntityHandle;
            handle.setPositionRotation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
            if (!l.getChunk().isLoaded()) {
                l.getChunk().load();
            }
            if (mcWorld.addEntity(handle, CreatureSpawnEvent.SpawnReason.CUSTOM)) {
                this.hasSpawned = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasSpawned() {
        return this.hasSpawned;
    }

    @Override
    public String getName() {
        if (this.handle == null) {
            return null;
        }
        return this.getBukkitEntity().getCustomName();
    }

    @Override
    public boolean setName(String name) {
        if (this.handle == null) {
            return false;
        }
        this.getBukkitEntity().setCustomName(name);
        this.getBukkitEntity().setCustomNameVisible(name == null ? false : true);
        return true;
    }

    @Override
    public Map<String, String> getSounds(EntitySound type) {
        return this.sounds.get(type);
    }

    @Override
    public String getSound(EntitySound type) {
        String custom = this.getCustomSound(type, "");
        if (custom != null && !custom.equals("")) {
            return custom;
        }
        return this.getSound(type, "default");
    }

    @Override
    public String getSound(EntitySound type, String key) {
        String custom = this.getCustomSound(type, key);
        if (custom != null && !custom.equals("")) {
            return custom;
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
    public String getCustomSound(EntitySound type, String key) {
        if (!key.equals("")) {
            String customWithKey = this.getSound(type, "custom." + key);
            if (customWithKey != null) {
                return customWithKey;
            }
        }
        String custom = this.getSound(type, "custom");
        if (custom != null) {
            return custom;
        }
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
        // Entities automatically use the "custom" sound if one exists
        this.setSound(type, "custom", CraftSound.getSound(sound));
    }

    @Override
    public void setSound(EntitySound type, Sound sound, String key) {
        this.setSound(type, "custom." + key, CraftSound.getSound(sound));
    }

    @Override
    public void setSound(EntitySound type, String sound) {
        this.setSound(type, "default", sound);
    }

    @Override
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

    @Override
    public void setSound(EntitySound type, HashMap<String, String> soundMap) {
        this.sounds.put(type, soundMap);
    }

    @Override
    public boolean canFly() {
        return this.canFly;
    }

    @Override
    public void setCanFly(boolean flag) {
        this.canFly = flag;
    }

    @Override
    public Material getLoot() {
        return loot;
    }

    @Override
    public void setLoot(Material material) {
        this.loot = material;
    }

    @Override
    public boolean shouldUpdateAttributes() {
        return tickAttributes;
    }

    @Override
    public void setTickAttributes(boolean flag) {
        this.tickAttributes = flag;
    }

    @Override
    public double getSpeed() {
        if (this.handle == null) {
            return GenericAttributes.d.b();
        }
        return this.handle.getAttributeInstance(GenericAttributes.d).getValue();
    }

    @Override
    public void setSpeed(double speed) {
        this.handle.getAttributeInstance(GenericAttributes.d).setValue(speed);
    }

    @Override
    public void setPathfindingRange(double range) {
        this.handle.getAttributeInstance(GenericAttributes.b).setValue(range);
    }

    @Override
    public double getPathfindingRange() {
        AttributeInstance attribute = this.handle.getAttributeInstance(GenericAttributes.b);
        return attribute == null ? 16.0D : attribute.getValue();
    }

    @Override
    public boolean navigateTo(LivingEntity livingEntity) {
        return this.navigateTo(livingEntity, this.getSpeed());
    }

    @Override
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

    @Override
    public boolean navigateTo(Location to, double speed) {
        if (to == null) {
            return false;
        }
        PathEntity path = this.handle.world.a(this.handle, MathHelper.floor(to.getX()), MathHelper.f(to.getY()), MathHelper.floor(to.getZ()), (float) this.getPathfindingRange(), true, false, false, true);
        return this.navigateTo(path, speed);
    }

    @Override
    public boolean navigateTo(PathEntity path, double speed) {
        if (!(this.handle instanceof EntityInsentient) || path == null) {
            return false;
        }
        return ((EntityInsentient) this.handle).getNavigation().a(path, speed);
    }

    @Override
    public LivingEntity getTarget() {
        if (this.handle == null) {
            return null;
        }

        if (this.handle instanceof EntityInsentient) {
            EntityLiving targetHandle = ((EntityInsentient) this.handle).getGoalTarget();
            if (targetHandle != null && targetHandle.getBukkitEntity() instanceof LivingEntity) {
                return (LivingEntity) targetHandle.getBukkitEntity();
            }
        }

        return null;
    }

    @Override
    public void setTarget(LivingEntity target) {
        if (this.handle == null) {
            return;
        }

        if (this.handle instanceof EntityInsentient) {
            ((EntityInsentient) this.handle).setGoalTarget(((CraftLivingEntity) target).getHandle());
        }
    }

    @Override
    public void onTick() {
        ControllableEntityTickEvent tickEvent = new ControllableEntityTickEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(tickEvent);
    }

    @Override
    public boolean onInteract(Player entity, boolean rightClick) {
        ControllableEntityInteractEvent interactEvent = new ControllableEntityInteractEvent(this, entity, rightClick ? Action.RIGHT_CLICK : Action.LEFT_CLICK);
        Bukkit.getServer().getPluginManager().callEvent(interactEvent);
        return !interactEvent.isCancelled();
    }

    @Override
    public Vector onPush(float x, float y, float z) {
        ControllableEntityPushEvent pushEvent = new ControllableEntityPushEvent(this, new Vector(x, y, z));
        Bukkit.getServer().getPluginManager().callEvent(pushEvent);
        return pushEvent.getPushVelocity();
    }

    @Override
    public boolean onCollide(Entity entity) {
        ControllableEntityCollideEvent collideEvent = new ControllableEntityCollideEvent(this, entity);
        Bukkit.getServer().getPluginManager().callEvent(collideEvent);
        return !collideEvent.isCancelled();
    }

    @Override
    public void onDeath() {
        ControllableEntityDeathEvent deathEvent = new ControllableEntityDeathEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(deathEvent);
        this.getMind().setControllableEntity(null);
    }

    public void initSounds() {
    }

    @Override
    public void setDefaultBehaviours() {
        //TODO
    }
}