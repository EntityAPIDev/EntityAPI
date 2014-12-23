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

import com.captainbern.reflection.Reflection;
import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.internal.Constants;

public enum ControllableEntityType {

    /**
     * Represents a Player entity
     */
    HUMAN("Player", "Human", -1, true),

    /**
     * Represents a Bat entity
     *
     * @see org.entityapi.api.entity.type.ControllableBat
     */
    BAT("Bat", Constants.EntityTypes.Names.ENTITY_BAT.getValue(), Constants.EntityTypes.Ids.ENTITY_BAT.getValue(), false),

    /**
     * Represents a Blaze entity
     *
     * @see org.entityapi.api.entity.type.ControllableBlaze
     */
    BLAZE("Blaze", Constants.EntityTypes.Names.ENTITY_BLAZE.getValue(), Constants.EntityTypes.Ids.ENTITY_BLAZE.getValue(), false),

    /**
     * Represents a CaveSpider entity
     *
     * @see org.entityapi.api.entity.type.ControllableCaveSpider
     */
    CAVE_SPIDER("CaveSpider", Constants.EntityTypes.Names.ENTITY_CAVE_SPIDER.getValue(), Constants.EntityTypes.Ids.ENTITY_CAVE_SPIDER.getValue(), false),

    /**
     * Represents a Chicken entity
     *
     * @see org.entityapi.api.entity.type.ControllableChicken
     */
    CHICKEN("Chicken", Constants.EntityTypes.Names.ENTITY_CHICKEN.getValue(), Constants.EntityTypes.Ids.ENTITY_CHICKEN.getValue(), false),

    /**
     * Represents a Cow entity
     *
     * @see org.entityapi.api.entity.type.ControllableCow
     */
    COW("Cow", Constants.EntityTypes.Names.ENTITY_COW.getValue(), Constants.EntityTypes.Ids.ENTITY_COW.getValue(), false),

    /**
     * Represents a Creeper entity
     *
     * @see org.entityapi.api.entity.type.ControllableCreeper
     */
    CREEPER("Creeper", Constants.EntityTypes.Names.ENTITY_CREEPER.getValue(), Constants.EntityTypes.Ids.ENTITY_CREEPER.getValue(), false),

    /**
     * Represents a EnderDragon entity
     *
     * @see org.entityapi.api.entity.type.ControllableEnderDragon
     */
    ENDERDRAGON("EnderDragon", Constants.EntityTypes.Names.ENTITY_ENDERDRAGON.getValue(), Constants.EntityTypes.Ids.ENTITY_ENDERDRAGON.getValue(), false),

    /**
     * Represents a Enderman entity
     *
     * @see org.entityapi.api.entity.type.ControllableEnderman
     */
    ENDERMAN("Enderman", Constants.EntityTypes.Names.ENTITY_ENDERMAN.getValue(), Constants.EntityTypes.Ids.ENTITY_ENDERMAN.getValue(), false),

    /**
     * Represents a Ghast entity
     *
     * @see org.entityapi.api.entity.type.ControllableGhast
     */
    GHAST("Ghast", Constants.EntityTypes.Names.ENTITY_GHAST.getValue(), Constants.EntityTypes.Ids.ENTITY_GHAST.getValue(), false),

    /**
     * Represents a GiantZombie entity
     *
     * @see org.entityapi.api.entity.type.ControllableGiantZombie
     */
    GIANT("GiantZombie", Constants.EntityTypes.Names.ENTITY_GIANT.getValue(), Constants.EntityTypes.Ids.ENTITY_GIANT.getValue(), false),

    /**
     * Represents an Horse entity
     *
     * @see org.entityapi.api.entity.type.ControllableHorse
     */
    HORSE("Horse", Constants.EntityTypes.Names.ENTITY_HORSE.getValue(), Constants.EntityTypes.Ids.ENTITY_HORSE.getValue(), false),

    /**
     * Represents an IronGolem entity
     *
     * @see org.entityapi.api.entity.type.ControllableIronGolem
     */
    IRON_GOLEM("IronGolem", Constants.EntityTypes.Names.ENTITY_IRON_GOLEM.getValue(), Constants.EntityTypes.Ids.ENTITY_IRON_GOLEM.getValue(), false),

    /**
     * Represents a MagmaCube entity
     *
     * @see org.entityapi.api.entity.type.ControllableMagmaCube
     */
    LAVA_SLIME("MagmaCube", Constants.EntityTypes.Names.ENTITY_LAVA_SLIME.getValue(), Constants.EntityTypes.Ids.ENTITY_LAVA_SLIME.getValue(), false),

    /**
     * Represents a MushroomCow entity
     *
     * @see org.entityapi.api.entity.type.ControllableMushroomCow
     */
    MUSHROOMCOW("MushroomCow", Constants.EntityTypes.Names.ENTITY_MUSHROOM_COW.getValue(), Constants.EntityTypes.Ids.ENTITY_MUSHROOM_COW.getValue(), false),

    /**
     * Represents an Ocelot entity
     *
     * @see org.entityapi.api.entity.type.ControllableOcelot
     */
    OCELOT("Ocelot", Constants.EntityTypes.Names.ENTITY_OZELOT.getValue(), Constants.EntityTypes.Ids.ENTITY_OZELOT.getValue(), false),

    /**
     * Represents a Pig entity
     *
     * @see org.entityapi.api.entity.type.ControllablePig
     */
    PIG("Pig", Constants.EntityTypes.Names.ENTITY_PIG.getValue(), Constants.EntityTypes.Ids.ENTITY_PIG.getValue(), false),

    /**
     * Represents a PigZombie entity
     *
     * @see org.entityapi.api.entity.type.ControllablePigZombie
     */
    PIG_ZOMBIE("PigZombie", Constants.EntityTypes.Names.ENTITY_PIG_ZOMBIE.getValue(), Constants.EntityTypes.Ids.ENTITY_PIG_ZOMBIE.getValue(), false),

    /**
     * Represents a Sheep entity
     *
     * @see org.entityapi.api.entity.type.ControllableSheep
     */
    SHEEP("Sheep", Constants.EntityTypes.Names.ENTITY_SHEEP.getValue(), Constants.EntityTypes.Ids.ENTITY_SHEEP.getValue(), false),

    /**
     * Represents a Silverfish entity
     *
     * @see org.entityapi.api.entity.type.ControllableSilverfish
     */
    SILVERFISH("Silverfish", Constants.EntityTypes.Names.ENTITY_SILVERFISH.getValue(), Constants.EntityTypes.Ids.ENTITY_SILVERFISH.getValue(), false),

    /**
     * Represents a Skeleton entity
     *
     * @see org.entityapi.api.entity.type.ControllableSkeleton
     */
    SKELETON("Skeleton", Constants.EntityTypes.Names.ENTITY_SKELETON.getValue(), Constants.EntityTypes.Ids.ENTITY_SKELETON.getValue(), false),

    /**
     * Represents a Slime entity
     *
     * @see org.entityapi.api.entity.type.ControllableSlime
     */
    SLIME("Slime", Constants.EntityTypes.Names.ENTITY_SLIME.getValue(), Constants.EntityTypes.Ids.ENTITY_SLIME.getValue(), false),

    /**
     * Represents a Snowman entity
     *
     * @see org.entityapi.api.entity.type.ControllableSnowman
     */
    SNOWMAN("Snowman", Constants.EntityTypes.Names.ENTITY_SNOWMAN.getValue(), Constants.EntityTypes.Ids.ENTITY_SNOWMAN.getValue(), false),

    /**
     * Represents a Spider entity
     *
     * @see org.entityapi.api.entity.type.ControllableSpider
     */
    SPIDER("Spider", Constants.EntityTypes.Names.ENTITY_SPIDER.getValue(), Constants.EntityTypes.Ids.ENTITY_SPIDER.getValue(), false),

    /**
     * Represents a Squid entity
     *
     * @see org.entityapi.api.entity.type.ControllableSquid
     */
    SQUID("Squid", Constants.EntityTypes.Names.ENTITY_SQUID.getValue(), Constants.EntityTypes.Ids.ENTITY_SQUID.getValue(), false),

    /**
     * Represents a Villager entity
     *
     * @see org.entityapi.api.entity.type.ControllableVillager
     */
    VILLAGER("Villager", Constants.EntityTypes.Names.ENTITY_VILLAGER.getValue(), Constants.EntityTypes.Ids.ENTITY_VILLAGER.getValue(), false),

    /**
     * Represents a Witch entity
     *
     * @see org.entityapi.api.entity.type.ControllableWitch
     */
    WITCH("Witch", Constants.EntityTypes.Names.ENTITY_WITCH.getValue(), Constants.EntityTypes.Ids.ENTITY_WITCH.getValue(), false),

    /**
     * Represents a Wither entity
     *
     * @see org.entityapi.api.entity.type.ControllableWither
     */
    WITHER("Wither", Constants.EntityTypes.Names.ENTITY_WITHER.getValue(), Constants.EntityTypes.Ids.ENTITY_WITHER.getValue(), false),

    /**
     * Represents a Wolf entity
     *
     * @see org.entityapi.api.entity.type.ControllableWolf
     */
    WOLF("Wolf", Constants.EntityTypes.Names.ENTITY_WOLF.getValue(), Constants.EntityTypes.Ids.ENTITY_WOLF.getValue(), false),

    /**
     * Represents a Zombie entity
     *
     * @see org.entityapi.api.entity.type.ControllableZombie
     */
    ZOMBIE("Zombie", Constants.EntityTypes.Names.ENTITY_ZOMBIE.getValue(), Constants.EntityTypes.Ids.ENTITY_ZOMBIE.getValue(), false);

    private final String name;
    private final int id;
    private final Class<? extends ControllableEntity> controllableInterface;
    private final Class<? extends ControllableEntity> controllableClass;
    private final Class<? extends ControllableEntityHandle> handleInterface;
    private final Class<? extends ControllableEntityHandle> handleClass;
    private final boolean isNameRequired;

    ControllableEntityType(String classPath, String name, int id, boolean isNameRequired) {
        this.controllableInterface = new Reflection().reflect("org.entityapi.api.entity.type.Controllable" + classPath).getReflectedClass();
        this.controllableClass = new Reflection().reflect("org.entityapi.api.entity.impl.Controllable" + classPath + "Base").getReflectedClass();
        this.handleInterface = new Reflection().reflect("org.entityapi.api.entity.type.nms.Controllable" + classPath + "Handle").getReflectedClass();
        this.handleClass = new Reflection().reflect(EntityAPI.INTERNAL_NMS_PATH + ".entity.Controllable" + classPath + "Entity").getReflectedClass();
        if (!ControllableEntityHandle.class.isAssignableFrom(handleClass)) {
            throw new RuntimeException("Handle class needs to implement ControllableEntityHandle!");
        }
        this.name = name;
        this.id = id;
        this.isNameRequired = isNameRequired;
    }

    /**
     * Gets the controllable entity type associated with a given controllable implementation class or interface
     *
     * @param clazz Class to retrieve the type of
     * @return Entity type tied to the given class or interface, or null if not found
     */
    public static ControllableEntityType getByControllableClass(Class<? extends ControllableEntity> clazz) {
        for (ControllableEntityType type : values()) {
            if (type.getControllableClass().equals(clazz) || type.getControllableInterface().equals(clazz)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Gets the controllable entity type associated with a given NMS handle class
     *
     * @param clazz Class to retrieve the type of
     * @return Entity type tied to the given class or interface, or null if not found
     */
    public static ControllableEntityType getByEntityClass(Class clazz) {
        for (ControllableEntityType type : values()) {
            if (type.getHandleClass().equals(clazz) || type.getHandleInterface().equals(clazz)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Gets the name of this entity type
     *
     * @return Name of this entity type
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the name of this entity type
     *
     * @return Name of this entity type
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets whether a name is required for this entity type
     *
     * @return True if a name is required, false if otherwise
     */
    public boolean isNameRequired() {
        return this.isNameRequired;
    }

    /**
     * Gets the interface associated with this entity type
     *
     * @return The interface associated with this entity type
     */
    public Class<? extends ControllableEntity> getControllableInterface() {
        return this.controllableInterface;
    }

    /**
     * Gets the implementation class associated with this entity type
     *
     * @return The implementation class associated with this entity type
     */
    public Class<? extends ControllableEntity> getControllableClass() {
        return this.controllableClass;
    }

    /**
     * Gets the NMS handle interface associated with this entity type
     *
     * @return The NMS handle class associated with this entity type
     */
    public Class<? extends ControllableEntityHandle> getHandleInterface() {
        return this.handleInterface;
    }

    /**
     * Gets the NMS handle class associated with this entity type
     *
     * @return The NMS handle class associated with this entity type
     */
    public Class<? extends ControllableEntityHandle> getHandleClass() {
        return this.handleClass;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
