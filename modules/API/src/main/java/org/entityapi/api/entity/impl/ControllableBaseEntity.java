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

package org.entityapi.api.entity.impl;

import com.captainbern.reflection.Reflection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.entityapi.api.EntityManager;
import org.entityapi.api.NMSAccessor;
import org.entityapi.api.entity.*;
import org.entityapi.api.entity.mind.Mind;
import org.entityapi.api.entity.mind.attribute.DespawnAttribute;
import org.entityapi.api.entity.mind.attribute.InventoryAttribute;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;
import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.game.GameRegistry;
import org.entityapi.game.IEntitySpawnHandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class ControllableBaseEntity<T extends LivingEntity, S extends ControllableEntityHandle<T>> implements ControllableEntity<T, S> {

    protected final EntityManager manager;
    private final NMSAccessor<T, S> accessor;
    private final int ID;

    protected Mind mind;
    protected boolean tickAttributes;
    protected boolean overridePathfindingSpeed;

    protected org.bukkit.Material loot;

    protected S handle;
    protected ControllableEntityType entityType;
    protected HashMap<EntitySound, Map<String, String>> sounds = new HashMap<>();

    public ControllableBaseEntity(int id, ControllableEntityType entityType, EntityManager manager) {
        this.accessor = (NMSAccessor<T, S>) new Reflection().reflect(EntityAPI.INTERNAL_NMS_PATH + ".NMSAccessorImpl").getSafeConstructor(ControllableEntity.class).getAccessor().invoke(this);
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
    public NMSAccessor<T, S> getNMSAccessor() {
        return accessor;
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
        return (T) accessor.getBukkitEntity();
    }

    @Override
    public S getHandle() {
        return this.handle;
    }

    @Override
    public ControllableEntityType getEntityType() {
        return this.entityType;
    }

    @Override
    public float getHeight() {
        return accessor.getHeight();
    }

    @Override
    public float getWidth() {
        return accessor.getWidth();
    }

    @Override
    public boolean isSpawned() {
        return this.handle != null;
    }

    @Override
    public SpawnResult spawn(Location location) {
        if (isSpawned()) {
            return SpawnResult.ALREADY_SPAWNED;
        }

        this.handle = GameRegistry.get(IEntitySpawnHandler.class).createEntityHandle(this, location);

        LivingEntity entity = this.getBukkitEntity();
        if (entity != null) {
            entity.setMetadata(EntityAPI.ENTITY_METADATA_MARKER, new FixedMetadataValue(EntityAPI.getCore(), true)); // Perhaps make this a key somewhere
            entity.setRemoveWhenFarAway(false);
        }

        //return spawned;
        return isSpawned() ? SpawnResult.SUCCESS : SpawnResult.FAILED;
    }

    @Override
    public void despawn(DespawnReason reason) {
        // FIXME: Actually use the DespawnReason

        if (this.getBukkitEntity() != null)
            this.getBukkitEntity().remove();

        this.handle = null;

        getMind().getAttribute(DespawnAttribute.class).call(this, reason);

        this.manager.despawn(this);
    }

    @Override
    public Inventory getInventory() {
        InventoryAttribute inventoryAttribute = this.getMind().getAttribute(InventoryAttribute.class);
        if (inventoryAttribute != null) {
            return inventoryAttribute.getInventory();
        }
        return null;
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
        this.getBukkitEntity().setCustomNameVisible(name != null);
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
        return requestSound(type, key);
    }

    private String requestSound(EntitySound type, String key) {
        Map<String, String> sounds = this.sounds.get(type);
        if (sounds == null) {
            return "";
        }
        String sound = sounds.get(key);
        return sound != null ? sound : "";
    }

    @Override
    public String getCustomSound(EntitySound type, String key) {
        if (!key.equals("")) {
            String customWithKey = this.requestSound(type, "custom." + key);
            if (customWithKey != null) {
                return customWithKey;
            }
        }
        String custom = this.requestSound(type, "custom");
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
            if (entry.getValue().equals(accessor.getSoundName(toReplace))) { // This way, we're doing the NMS stuff
                newKey = entry.getKey();
                removed = true;
                i.remove();
            }
        }

        if (replaceWith != null) { // Allows sounds to be removed
            if (removed || addOnFail) { // On fail - offer the option to add the sound anyway. Functions as setSound(type, sound) too
                this.setSound(type, newKey, accessor.getSoundName(replaceWith));
            }
        }
    }

    @Override
    public void setSound(EntitySound type, Sound sound) {
        // Allows sounds to be set without the use of the NMS String
        // We can also allow people to add/replace/remove sounds
        // Entities automatically use the "custom" sound if one exists
        this.setSound(type, "custom", accessor.getSoundName(sound));
    }

    @Override
    public void setSound(EntitySound type, Sound sound, String key) {
        this.setSound(type, "custom." + key, accessor.getSoundName(sound));
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
    public boolean isPathfindingSpeedOverriden() {
        return overridePathfindingSpeed;
    }

    @Override
    public double getSpeed() {
        return accessor.getSpeed();
    }

    @Override
    public void setSpeed(double speed) {
        this.setSpeed(speed, false);
    }

    @Override
    public void setSpeed(double speed, boolean overridePathfindingSpeed) {
        this.overridePathfindingSpeed = overridePathfindingSpeed;
        accessor.setSpeed(speed);
    }

    @Override
    public void setPathfindingRange(double range) {
        accessor.setPathfindingRange(range);
    }

    @Override
    public double getPathfindingRange() {
        return accessor.getPathfindingRange();
    }

    @Override
    public boolean navigateTo(LivingEntity livingEntity) {
        return this.navigateTo(livingEntity, this.getSpeed());
    }

    @Override
    public boolean navigateTo(LivingEntity livingEntity, double speed) {
        return accessor.navigateTo(livingEntity, speed);
    }

    @Override
    public boolean navigateTo(Location to) {
        return this.navigateTo(to.toVector());
    }

    @Override
    public boolean navigateTo(Location to, double speed) {
        return this.navigateTo(to.toVector(), speed);
    }

    @Override
    public boolean navigateTo(Vector to) {
        return this.navigateTo(to, this.getSpeed());
    }

    @Override
    public boolean navigateTo(Vector to, double speed) {
        return accessor.navigateTo(to, speed);
    }

    @Override
    public void lookAt(Location location) {
        if (!this.isSpawned())
            return;

        this.accessor.lookAt(location);
    }

    @Override
    public void lookAt(Entity entity) {
        if (!this.isSpawned())
            return;

        this.accessor.lookAt(entity);
    }

    @Override
    public LivingEntity getTarget() {
        return accessor.getTarget();
    }

    @Override
    public void setTarget(LivingEntity target) {
        accessor.setTarget(target);
    }

    public void initSounds() {
    }

    @Override
    public void setDefaultBehaviours() {
        this.getMind().getMovementBehaviourSelector().clearBehaviours();
        for (BehaviourItem behaviourItem : this.getDefaultMovementBehaviours()) {
            this.getMind().getMovementBehaviourSelector().addBehaviour(behaviourItem.getBehaviour(), behaviourItem.getPriority());
        }

        for (BehaviourItem behaviourItem : this.getDefaultTargetingBehaviours()) {
            this.getMind().getTargetingBehaviourSelector().addBehaviour(behaviourItem.getBehaviour(), behaviourItem.getPriority());
        }
    }

    @Override
    public BehaviourItem[] getDefaultMovementBehaviours() {
        return new BehaviourItem[0];
    }

    @Override
    public BehaviourItem[] getDefaultTargetingBehaviours() {
        return new BehaviourItem[0];
    }

    @Override
    public boolean isStationary() {
        return this.getMind().isStationary();
    }

    @Override
    public void setStationary(boolean flag) {
        this.getMind().setStationary(flag);
    }

    @Override
    public void setFixedYaw(float value) {
        if (this.getMind().isStationary()) {
            this.getMind().setFixedYaw(value);
        }
    }

    @Override
    public void setFixedHeadYaw(float value) {
        if (this.getMind().isStationary()) {
            this.getMind().setFixedHeadYaw(value);
        }
    }

    @Override
    public void setFixedPitch(float value) {
        if (this.getMind().isStationary()) {
            this.getMind().setFixedPitch(value);
        }
    }
}
