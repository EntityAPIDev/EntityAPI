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

package org.entityapi.api.entity;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.util.Vector;
import org.entityapi.api.EntityManager;
import org.entityapi.api.NMSAccessor;
import org.entityapi.api.entity.mind.Mind;
import org.entityapi.api.entity.mind.attribute.ControlledRidingAttribute;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a ControllableEntity
 * <p/>
 * This acts as a base class for all other entity types to stem off
 *
 * @param <T> the Bukkit entity that this ControllableEntity represents
 * @param <S> the entity handle that this ControllableEntity represents, useful for interacting with NMS entities
 */
public interface ControllableEntity<T extends LivingEntity, S extends ControllableEntityHandle<T>> extends Nameable, Attacking, InventoryHolder {

    /**
     * Gets the EntityManager from which this ControllableEntity was created
     *
     * @return the EntityManager for this entity
     */
    EntityManager getEntityManager();

    /**
     * Returns an accessor for certain NMS methods
     * <p/>
     * <strong>Not recommended for public API consumption</strong>
     *
     * @return the accessor to be utilised for this entity
     */
    NMSAccessor<T, S> getNMSAccessor();

    /**
     * Gets the ID of this entity
     * <p/>
     * Entity IDs are unique to this entity until it is removed from its corresponding {@link
     * org.entityapi.api.EntityManager}. Once removed, this entity ID will be recycled and applied to another entity.
     * It
     * is therefore important NOT to utilise this ID as a storage mechanism unless the appropriate hooks are in place
     * for when the stored IDs reference another entity
     *
     * @return the ID that corresponds to this entity
     */
    int getId();

    /**
     * Gets the mind of this entity
     * <p/>
     * The entity mind is the hub for all general entity activity. It makes use of priorities behaviour and attribute
     * ticking systems, taking into account those changes made through the API.
     * <p/>
     * Most activity will take place in the mind, or 'brain', of the entity
     *
     * @return the mind of this entity
     */
    Mind getMind();

    /**
     * Gets the Bukkit alternative that corresponds to this entity
     * <p/>
     * Often, certain functions have not been included in EntityAPI as a Bukkit alternative already exists
     *
     * @return the Bukkit entity that represents this ControllableEntity
     */
    T getBukkitEntity();

    /**
     * Gets the NMS entity handle for this ControllableEntity
     * <strong>Not recommended for public API consumption</strong>
     *
     * @return the NMS handle for this entity
     */
    S getHandle();

    /**
     * Gets the type of this entity
     * <p/>
     * Types can be most appropriately used in a similar fashion to {@link org.bukkit.entity.EntityType}
     *
     * @return the type of this entity
     */
    ControllableEntityType getEntityType();

    /**
     * Gets the height of this entity's bounding box
     *
     * @return the height of the bounding box surrounding this entity
     */
    float getHeight();

    /**
     * Gets the width of this entity's bounding box
     *
     * @return the width of the bounding box surrounding this entity
     */
    float getWidth();

    /**
     * Spawns this ControllableEntity at the specified location
     * <p/>
     * Returns a {@link org.entityapi.api.entity.SpawnResult} representing the success of spawning this entity
     *
     * @param location the Location to spawn this entity at
     * @return result of spawning this entity at the given location
     * @see org.entityapi.api.entity.SpawnResult
     */
    SpawnResult spawn(Location location);

    /**
     * Despawns this ControllableEntity
     * <p/>
     * Despawning an entity removes it from both the current world and EntityManager until respawned. This entity's
     * mind, attributes and behaviours will remain intact for later use
     * <p/>
     * Plugins should make use of {@link org.entityapi.api.entity.DespawnReason#CUSTOM} unless under specific
     * circumstances where another reason may be more appropriate
     *
     * @param despawnReason the reason this entity was despawned
     */
    void despawn(DespawnReason despawnReason);

    /**
     * Returns whether this ControllableEntity is currently spawned
     * <p/>
     * This will return true if the entity has despawned, possibly due to one of the following reasons:
     * <ul>
     * <li>This entity dies from external sources</li>
     * <li>This entity is despawned using the {@link #despawn(DespawnReason)} method</li>
     * <li>The chunk this entity is positioned in despawns. In this case, the entity will be automatically respanwned
     * when the chunk is loaded again</li>
     * </ul>
     *
     * @return true if this entity is currently living and spawned in a world
     */
    boolean isSpawned();

    @Override
    String getName();

    @Override
    boolean setName(String name);

    Map<String, String> getSounds(EntitySound type);

    String getSound(EntitySound type);

    String getSound(EntitySound type, String key);

    String getCustomSound(EntitySound type, String key);

    void setSound(EntitySound type, Sound toReplace, Sound replaceWith, boolean addOnFail);

    void setSound(EntitySound type, Sound sound);

    void setSound(EntitySound type, Sound sound, String key);

    void setSound(EntitySound type, String sound);

    void setSound(EntitySound type, String key, String sound);

    void setSound(EntitySound type, HashMap<String, String> soundMap);

    Material getLoot();

    void setLoot(Material material);

    boolean shouldUpdateAttributes();

    void setTickAttributes(boolean flag);

    boolean isPathfindingSpeedOverriden();

    double getSpeed();

    void setSpeed(double speed);

    void setSpeed(double speed, boolean overridePathfindingSpeed);

    double getPathfindingRange();

    void setPathfindingRange(double range);

    boolean navigateTo(LivingEntity livingEntity);

    boolean navigateTo(LivingEntity livingEntity, double speed);

    boolean navigateTo(Location to);

    boolean navigateTo(Location to, double speed);

    boolean navigateTo(Vector to);

    boolean navigateTo(Vector to, double speed);

    /**
     * Resets the behavioural features of this entity back to default
     * <p/>
     * Any existing behaviours will be cleared from this entity's mind, with the default behaviours being applied. The
     * default behaviours for an entity can be accessed using {@link #getDefaultMovementBehaviours()} and {@link
     * #getDefaultTargetingBehaviours()}
     */
    void setDefaultBehaviours();

    /**
     * Gets the default movement based behaviours prescribed to this entity
     *
     * @return the default movement behaviours for this entity, or an empty array if none exist
     */
    BehaviourItem[] getDefaultMovementBehaviours();

    /**
     * Gets the default targeting based behaviours prescribed to this entity
     *
     * @return the default targeting behaviours for this entity, or an empty array if none exist
     */
    BehaviourItem[] getDefaultTargetingBehaviours();

    /**
     * Gets whether this entity is currently in a stationary state
     * <p/>
     * All movement for a stationary entity will be prevented. Any pitch and yaw rotations will remain fixed while an
     * entity is stationary.
     *
     * @return true if this entity is currently stationary
     */
    boolean isStationary();

    /**
     * Sets the whether or not this entity is stationary (not moving)
     * <p/>
     * All movement for a stationary entity will be prevented. Any pitch and yaw rotations will remain fixed while an
     * entity is stationary.
     * <p/>
     * Current pitch and yaw rotations will be applied upon being set to a stationary status
     *
     * @param flag true if this entity is to be stationary
     */
    void setStationary(boolean flag);

    /**
     * Sets the yaw of this entity's location as a fixed value, measured in degrees.
     * <ul>
     * <li>A yaw of 0 or 360 represents the positive z direction.
     * <li>A yaw of 180 represents the negative z direction.
     * <li>A yaw of 90 represents the negative x direction.
     * <li>A yaw of 270 represents the positive x direction.
     * </ul>
     * Increasing yaw values are the equivalent of turning to your
     * right-facing, increasing the scale of the next respective axis, and
     * decreasing the scale of the previous axis.
     *
     * @param value the new yaw rotation
     * @see Location#setYaw(float)
     */
    void setFixedYaw(float value);

    /**
     * Sets the head yaw of this entity's location as a fixed value, measured in degrees.
     * <ul>
     * <li>A yaw of 0 or 360 represents the positive z direction.
     * <li>A yaw of 180 represents the negative z direction.
     * <li>A yaw of 90 represents the negative x direction.
     * <li>A yaw of 270 represents the positive x direction.
     * </ul>
     * Increasing yaw values are the equivalent of turning to your
     * right-facing, increasing the scale of the next respective axis, and
     * decreasing the scale of the previous axis.
     *
     * @param value the new head yaw rotation
     * @see Location#setYaw(float)
     */
    void setFixedHeadYaw(float value);

    /**
     * Sets the pitch of this entity's location as a fixed value, measured in degrees.
     * <ul>
     * <li>A pitch of 0 represents level forward facing.
     * <li>A pitch of 90 represents downward facing, or negative y
     * direction.
     * <li>A pitch of -90 represents upward facing, or positive y direction.
     * <ul>
     * Increasing pitch values the equivalent of looking down.
     * <strong>Note: A fixed pitch value will only apply to a stationary entity</strong>
     *
     * @param value the new pitch rotation
     * @see Location#setPitch(float) (float)
     * @see #setStationary(boolean)
     */
    void setFixedPitch(float value);
}
