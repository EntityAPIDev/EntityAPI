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
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.util.Vector;
import org.entityapi.api.EntityManager;
import org.entityapi.api.NMSAccessor;
import org.entityapi.api.entity.mind.Mind;
import org.entityapi.api.entity.mind.behaviour.BehaviourItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a ControllableEntity
 * <p/>
 * This acts as a base class for all other entity types to stem off.
 * <p/>
 * Most existing methods available in the entity portion of the Bukkit API will not be provided/proxied by EntityAPI.
 *
 * @param <T> The Bukkit entity that this ControllableEntity represents
 * @param <S> The entity handle that this ControllableEntity represents, useful for interacting with NMS entities
 */
public interface ControllableEntity<T extends LivingEntity, S extends ControllableEntityHandle<T>> extends Nameable, Attacking, InventoryHolder {

    /**
     * Gets the EntityManager from which this ControllableEntity was created
     *
     * @return The EntityManager for this entity
     */
    EntityManager getEntityManager();

    /**
     * Returns an accessor for certain NMS methods
     * <p/>
     * <strong>Not recommended for public API consumption</strong>
     *
     * @return The accessor to be utilised for this entity
     */
    NMSAccessor<T, S> getNMSAccessor();

    /**
     * Gets the ID of this entity
     * <p/>
     * Entity IDs are unique to this entity until it is removed from its corresponding {@link
     * org.entityapi.api.EntityManager}. Once removed, this entity ID will be recycled and applied to another entity. It
     * is therefore important NOT to utilise this ID as a storage mechanism unless the appropriate hooks are in place
     * for when the stored IDs reference another entity
     *
     * @return The ID that corresponds to this entity
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
     * @return The mind of this entity
     */
    Mind getMind();

    /**
     * Gets the Bukkit alternative that corresponds to this entity
     * <p/>
     * Often, certain functions have not been included in EntityAPI as a Bukkit alternative already exists
     *
     * @return The Bukkit entity that represents this ControllableEntity
     */
    T getBukkitEntity();

    /**
     * Gets the NMS entity handle for this ControllableEntity <strong>Not recommended for public API
     * consumption</strong>
     *
     * @return The NMS handle for this entity
     */
    S getHandle();

    /**
     * Gets the type of this entity
     * <p/>
     * Types can be most appropriately used in a similar fashion to {@link org.bukkit.entity.EntityType}
     *
     * @return The type of this entity
     */
    ControllableEntityType getEntityType();

    /**
     * Gets the height of this entity's bounding box
     *
     * @return The height of the bounding box surrounding this entity
     */
    float getHeight();

    /**
     * Gets the width of this entity's bounding box
     *
     * @return The width of the bounding box surrounding this entity
     */
    float getWidth();

    /**
     * Spawns this ControllableEntity at the specified location
     * <p/>
     * Returns a {@link org.entityapi.api.entity.SpawnResult} representing the success of spawning this entity
     *
     * @param location The Location to spawn this entity at
     * @return Result of spawning this entity at the given location
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
     * @param despawnReason The reason this entity was despawned
     */
    void despawn(DespawnReason despawnReason);

    /**
     * Returns whether this ControllableEntity is currently spawned
     * <p/>
     * This will return true if the entity has despawned, possibly due to one of the following reasons:
     * <ul>
     *     <li>This entity dies from external sources</li>
     *     <li>This entity is despawned using the {@link #despawn(DespawnReason)} method</li>
     *     <li>The chunk this entity is positioned in despawns. In this case, the entity will be automatically
     *     respawned when the chunk is loaded again</li>
     * </ul>
     *
     * @return True if this entity is currently living and spawned in a world
     */
    boolean isSpawned();

    @Override
    String getName();

    @Override
    boolean setName(String name);

    /**
     * Gets the sounds that are currently applied to this entity for a given {@link
     * org.entityapi.api.entity.EntitySound}. This sound will only be utilised if the underlying entity makes use of the
     * given EntitySound.
     * <p/>
     * Entities will not have sounds that are applicable to all EntitySound types. It is important that you refer to the
     * documentation for the underlying entity class before making modifying any entity sounds.
     * <p/>
     * Depending on the entity type, an entity may have multiple sounds applicable to a certain EntitySound. For
     * example, a wolf has four different sounds mapped under the {@link EntitySound#IDLE} type:
     * <ul>
     *     <li>"growl" - "mob.wolf.growl"</li>
     *     <li>"whine" - "mob.wolf.whine"</li>
     *     <li>"panting" - "mob.wolf.panting"</li>
     *     <li>"bark" - "mob.wolf.bark"</li>
     * </ul>
     *
     * <p/>
     * Each of these sounds is accessed internally through use of a predefined key, in this
     * case on of the four above. This method returns a map of key-value pairings for each sound key that is applicable
     * to the underlying entity type. In the case of a wolf entity, this map would contain four entries, as listed
     * above.
     * <p/>
     * Custom sounds are always used for an entity in preference to any default sounds that may already exist.
     *
     * @param type Type of sound to retrieve
     * @return A map of all key-value sound pairings applicable to the given sound {@code type}
     */
    Map<String, String> getSounds(EntitySound type);

    /**
     * Retrieves the sound tied to the given {@link org.entityapi.api.entity.EntitySound} type.
     * <p/>
     * Please refer to the documentation attached to {@link #getSounds(EntitySound)} for more information on how sounds
     * are stored.
     *
     * @param type Type of sound to retrieve
     * @return If a custom sound is present for the default key associated with the supplied sound type, it will be
     * returned. Otherwise, the default sound will instead be returned. If no sound is currently mapped to the given
     * EntitySound type, an empty string will instead be returned.
     */
    String getSound(EntitySound type);

    /**
     * Retrieves the sound tied to the given {@link org.entityapi.api.entity.EntitySound} type with the provided {@code
     * key}.
     * <p/>
     * Please refer to the documentation attached to {@link #getSounds(EntitySound)} for more information on how sounds
     * are stored.
     *
     * @param type Type of sound to retrieve
     * @param key  The key to request a sound for. Refer to the documentation provided with the underlying entity type
     *             for a list of applicable keys.
     * @return If a custom sound is present for the given key when associated with the supplied sound type, it will be
     * returned. Otherwise, the default sound attached to that key will instead be returned. If no sound is currently
     * mapped to the given EntitySound type, an empty string will instead be returned.
     */
    String getSound(EntitySound type, String key);

    /**
     * Retrieves the custom sound tied to the given {@link org.entityapi.api.entity.EntitySound} type with the provided
     * {@code key}.
     * <p/>
     * Please refer to the documentation attached to {@link #getSounds(EntitySound)} for more information on how sounds
     * are stored.
     *
     * @param type Type of sound to retrieve
     * @param key  The key to request a sound for. Refer to the documentation provided with the underlying entity type
     *             for a list of applicable keys.
     * @return The custom sound tied to the given key, or an empty String if no custom sound has been added
     */
    String getCustomSound(EntitySound type, String key);

    /**
     * Replaces an existing entity sound with a custom one for a specified {@link org.entityapi.api.entity.EntitySound}
     * type.
     * <p/>
     * Please refer to the documentation attached to {@link #getSounds(EntitySound)} for more information on how sounds
     * are stored.
     *
     * @param type        Type of sound to replace
     * @param toReplace   Sound to replace
     * @param replaceWith New sound to be applied
     * @param addOnFail   If set to true and {@code toReplace} is not found in this entity's sound map, {@code
     *                    replaceWith} will be added to this entity anyway (via {@link #setSound(EntitySound,
     *                    org.bukkit.Sound)})
     */
    void setSound(EntitySound type, Sound toReplace, Sound replaceWith, boolean addOnFail);

    /**
     * Applies a new custom sound to a specified {@link org.entityapi.api.entity.EntitySound} type.
     * <p/>
     * Custom sounds are always used for an entity in preference to any default sounds that may already exist.
     * <p/>
     * Please refer to the documentation attached to {@link #getSounds(EntitySound)} for more information on how sounds
     * are stored.
     *
     * @param type  Type of sound to apply
     * @param sound Custom sound to apply to this entity
     */
    void setSound(EntitySound type, Sound sound);

    /**
     * Applies a new custom sound to a specified {@link org.entityapi.api.entity.EntitySound} type and key.
     * <p/>
     * Custom sounds are always used for an entity in preference to any default sounds that may already exist.
     * <p/>
     * Please refer to the documentation attached to {@link #getSounds(EntitySound)} for more information on how sounds
     * are stored.
     *
     * @param type  Type of sound to apply
     * @param sound Custom sound to apply to this entity
     * @param key   The key to apply the sound to. Refer to the documentation provided with the underlying entity type
     *              for a list of applicable keys.
     */
    void setSound(EntitySound type, Sound sound, String key);

    /**
     * Applies a new custom sound to a specified {@link org.entityapi.api.entity.EntitySound} type. Note that
     * {@link #setSound(EntitySound, org.bukkit.Sound)} should be use in preference to this method.
     * <p/>
     * Custom sounds are always used for an entity in preference to any default sounds that may already exist.
     * <p/>
     * Please refer to the documentation attached to {@link #getSounds(EntitySound)} for more information on how sounds
     * are stored.
     *
     * @param type  Type of sound to apply
     * @param sound Custom sound to apply to this entity
     */
    void setSound(EntitySound type, String sound);

    /**
     * Applies a new custom sound to a specified {@link org.entityapi.api.entity.EntitySound} type and key. Note that
     * {@link #setSound(EntitySound, org.bukkit.Sound, String)} should be use in preference to this method.
     * <p/>
     * Custom sounds are always used for an entity in preference to any default sounds that may already exist.
     * <p/>
     * Please refer to the documentation attached to {@link #getSounds(EntitySound)} for more information on how sounds
     * are stored.
     *
     * @param type  Type of sound to apply
     * @param sound The key to apply the sound to. Refer to the documentation provided with the underlying entity type
     *              for a list of applicable keys.
     * @param key   Custom sound to apply to this entity
     */
    void setSound(EntitySound type, String sound, String key);

    /**
     * Applies a map of key-value sound pairings to this entity for the specified {@link
     * org.entityapi.api.entity.EntitySound} type.
     * <p/>
     * This method essentially a bulk-handler for {@link #setSound(EntitySound, org.bukkit.Sound, String)}
     *
     * @param type     Type of sound to apply
     * @param soundMap A key to Sound map to be applied to this entity
     */
    void setSound(EntitySound type, HashMap<String, Sound> soundMap);

    /**
     * Sets whether this entity's {@link org.entityapi.api.entity.mind.Mind} should be ticked.
     *
     * @return True if this entity's Mind should be ticket, false if not
     */
    boolean shouldTickMind();

    /**
     * Setting this to false will halt all AI processes and temporarily disable any {@link
     * org.entityapi.api.entity.mind.behaviour.Behaviour}s from functioning
     *
     * @param flag True if this entity's mind should be ticked, false if not
     */
    void setTickMind(boolean flag);

    /**
     * Gets whether the pathfinding speed for this entity is currently being overridden
     *
     * @return True if pathfinding speed is overridden
     */
    boolean isPathfindingSpeedOverridden();

    /**
     * Gets the navigation speed of this entity.
     *
     * @return Navigation speed of this entity
     */
    double getSpeed();

    /**
     * Sets the navigation speed of this entity.
     *
     * @param speed New navigation speed
     */
    void setSpeed(double speed);

    /**
     * Sets the navigation speed of this entity.
     *
     * @param speed                    New navigation speed
     * @param overridePathfindingSpeed If true, all pathfinding will run at {@code speed}
     */
    void setSpeed(double speed, boolean overridePathfindingSpeed);

    /**
     * Gets the range of this entity's pathfinding
     *
     * @return Range of this entity's pathfinding
     */
    double getPathfindingRange();

    /**
     * Sets the range of this entity's pathfinding.
     *
     * @param range New pathfinding range
     */
    void setPathfindingRange(double range);

    /**
     * Indicates to the entity that it is to navigate to a given LivingEntity. Navigation will be conducted at the speed
     * returned by {@link #getSpeed()} (which can be set via {@link #setSpeed(double)}
     *
     * @param livingEntity Target entity for this entity to navigate to
     * @return True if the navigation was successful, false if otherwise
     */
    boolean navigateTo(LivingEntity livingEntity);

    /**
     * Indicates to this entity that it is to navigate to a given LivingEntity at a certain speed.
     *
     * @param livingEntity Target entity for this entity to navigate to
     * @param speed        Speed to navigate at
     * @return True if the navigation was successful, false if otherwise
     */
    boolean navigateTo(LivingEntity livingEntity, double speed);

    /**
     * Indicates to this entity that it is to navigate to the coordinates of the given {@link org.bukkit.Location} in
     * the current world. The world of the given Location is ignored. Navigation will be conducted at the speed returned
     * by {@link #getSpeed()} (which can be set via {@link #setSpeed(double)}
     *
     * @param to Position to navigate to
     * @return True if the navigation was successful, false if otherwise
     */
    boolean navigateTo(Location to);

    /**
     * Indicates to this entity that it is to navigate to the coordinates of the given {@link org.bukkit.Location} in
     * the current world at a certain speed. The world of the given Location is ignored.
     *
     * @param to    Position to navigate to
     * @param speed Speed to navigate at
     * @return True if the navigation was successful, false if otherwise
     */
    boolean navigateTo(Location to, double speed);

    /**
     * Indicates to this entity that it is to navigate to the given coordinates in the current world.Navigation will be
     * conducted at the speed returned by {@link #getSpeed()} (which can be set via {@link #setSpeed(double)}
     *
     * @param x X-coordinate to navigate to
     * @param y Y-coordinate to navigate to
     * @param z Z-coordinate to navigate to
     * @return True if the navigation was successful, false if otherwise
     */
    boolean navigateTo(double x, double y, double z);

    /**
     * Indicates to this entity that it is to navigate to the given coordinates in the current world at a certain
     * speed.
     *
     * @param x     X-coordinate to navigate to
     * @param y     Y-coordinate to navigate to
     * @param z     Z-coordinate to navigate to
     * @param speed Speed to navigate at
     * @return True if the navigation was successful, false if otherwise
     */
    boolean navigateTo(double x, double y, double z, double speed);

    /**
     * Forces this entity to look towards a specific location
     *
     * @param location Location to look towards
     */
    void lookAt(Location location);

    /**
     * Forces this entity to look towards a specific entity
     *
     * @param entity Entity to look at
     */
    void lookAt(Entity entity);

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
     * Sets the yaw of this entity's location as a fixed value, measured in degrees. <ul> <li>A yaw of 0 or 360
     * represents the positive z direction. <li>A yaw of 180 represents the negative z direction. <li>A yaw of 90
     * represents the negative x direction. <li>A yaw of 270 represents the positive x direction. </ul> Increasing yaw
     * values are the equivalent of turning to your right-facing, increasing the scale of the next respective axis, and
     * decreasing the scale of the previous axis.
     *
     * @param value the new yaw rotation
     * @see Location#setYaw(float)
     */
    void setFixedYaw(float value);

    /**
     * Sets the head yaw of this entity's location as a fixed value, measured in degrees. <ul> <li>A yaw of 0 or 360
     * represents the positive z direction. <li>A yaw of 180 represents the negative z direction. <li>A yaw of 90
     * represents the negative x direction. <li>A yaw of 270 represents the positive x direction. </ul> Increasing yaw
     * values are the equivalent of turning to your right-facing, increasing the scale of the next respective axis, and
     * decreasing the scale of the previous axis.
     *
     * @param value the new head yaw rotation
     * @see Location#setYaw(float)
     */
    void setFixedHeadYaw(float value);

    /**
     * Sets the pitch of this entity's location as a fixed value, measured in degrees. <ul> <li>A pitch of 0 represents
     * level forward facing. <li>A pitch of 90 represents downward facing, or negative y direction. <li>A pitch of -90
     * represents upward facing, or positive y direction. </ul> Increasing pitch values the equivalent of looking down.
     * <strong>Note: A fixed pitch value will only apply to a stationary entity</strong>
     *
     * @param value the new pitch rotation
     * @see Location#setPitch(float) (float)
     * @see #setStationary(boolean)
     */
    void setFixedPitch(float value);
}
