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
import org.entityapi.api.entity.impl.ControllableBaseEntity;
import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.internal.Constants;

public enum ControllableEntityType {

    HUMAN("Player", "Human", -1, true),
    BAT("Bat", Constants.EntityTypes.Names.ENTITY_BAT.getValue(), Constants.EntityTypes.Ids.ENTITY_BAT.getValue(), false),
    BLAZE("Blaze", Constants.EntityTypes.Names.ENTITY_BLAZE.getValue(), Constants.EntityTypes.Ids.ENTITY_BLAZE.getValue(), false),
    CAVE_SPIDER("CaveSpider", Constants.EntityTypes.Names.ENTITY_CAVE_SPIDER.getValue(), Constants.EntityTypes.Ids.ENTITY_CAVE_SPIDER.getValue(), false),
    CHICKEN("Chicken", Constants.EntityTypes.Names.ENTITY_CHICKEN.getValue(), Constants.EntityTypes.Ids.ENTITY_CHICKEN.getValue(), false),
    COW("Cow", Constants.EntityTypes.Names.ENTITY_COW.getValue(), Constants.EntityTypes.Ids.ENTITY_COW.getValue(), false),
    CREEPER("Creeper", Constants.EntityTypes.Names.ENTITY_CREEPER.getValue(), Constants.EntityTypes.Ids.ENTITY_CREEPER.getValue(), false),
    ENDERDRAGON("EnderDragon", Constants.EntityTypes.Names.ENTITY_ENDERDRAGON.getValue(), Constants.EntityTypes.Ids.ENTITY_ENDERDRAGON.getValue(), false),
    ENDERMAN("Enderman", Constants.EntityTypes.Names.ENTITY_ENDERMAN.getValue(), Constants.EntityTypes.Ids.ENTITY_ENDERMAN.getValue(), false),
    GHAST("Ghast", Constants.EntityTypes.Names.ENTITY_GHAST.getValue(), Constants.EntityTypes.Ids.ENTITY_GHAST.getValue(), false),
    GIANT("GiantZombie", Constants.EntityTypes.Names.ENTITY_GIANT.getValue(), Constants.EntityTypes.Ids.ENTITY_GIANT.getValue(), false),
    HORSE("Horse", Constants.EntityTypes.Names.ENTITY_HORSE.getValue(), Constants.EntityTypes.Ids.ENTITY_HORSE.getValue(), false),
    IRON_GOLEM("IronGolem", Constants.EntityTypes.Names.ENTITY_IRON_GOLEM.getValue(), Constants.EntityTypes.Ids.ENTITY_IRON_GOLEM.getValue(), false),
    LAVA_SLIME("MagmaCube", Constants.EntityTypes.Names.ENTITY_LAVA_SLIME.getValue(), Constants.EntityTypes.Ids.ENTITY_LAVA_SLIME.getValue(), false),
    MUSHROOMCOW("MushroomCow", Constants.EntityTypes.Names.ENTITY_MUSHROOM_COW.getValue(), Constants.EntityTypes.Ids.ENTITY_MUSHROOM_COW.getValue(), false),
    OZELOT("Ocelot", Constants.EntityTypes.Names.ENTITY_OZELOT.getValue(), Constants.EntityTypes.Ids.ENTITY_OZELOT.getValue(), false),
    PIG("Pig", Constants.EntityTypes.Names.ENTITY_PIG.getValue(), Constants.EntityTypes.Ids.ENTITY_PIG.getValue(), false),
    PIG_ZOMBIE("PigZombie", Constants.EntityTypes.Names.ENTITY_PIG_ZOMBIE.getValue(), Constants.EntityTypes.Ids.ENTITY_PIG_ZOMBIE.getValue(), false),
    SHEEP("Sheep", Constants.EntityTypes.Names.ENTITY_SHEEP.getValue(), Constants.EntityTypes.Ids.ENTITY_SHEEP.getValue(), false),
    SILVERFISH("Silverfish", Constants.EntityTypes.Names.ENTITY_SILVERFISH.getValue(), Constants.EntityTypes.Ids.ENTITY_SILVERFISH.getValue(), false),
    SKELETON("Skeleton", Constants.EntityTypes.Names.ENTITY_SKELETON.getValue(), Constants.EntityTypes.Ids.ENTITY_SKELETON.getValue(), false),
    SLIME("Slime", Constants.EntityTypes.Names.ENTITY_SLIME.getValue(), Constants.EntityTypes.Ids.ENTITY_SLIME.getValue(), false),
    SNOWMAN("Snowman", Constants.EntityTypes.Names.ENTITY_SNOWMAN.getValue(), Constants.EntityTypes.Ids.ENTITY_SNOWMAN.getValue(), false),
    SPIDER("Spider", Constants.EntityTypes.Names.ENTITY_SPIDER.getValue(), Constants.EntityTypes.Ids.ENTITY_SPIDER.getValue(), false),
    SQUID("Squid", Constants.EntityTypes.Names.ENTITY_SQUID.getValue(), Constants.EntityTypes.Ids.ENTITY_SQUID.getValue(), false),
    VILLAGER("Villager", Constants.EntityTypes.Names.ENTITY_VILLAGER.getValue(), Constants.EntityTypes.Ids.ENTITY_VILLAGER.getValue(), false),
    WITCH("Witch", Constants.EntityTypes.Names.ENTITY_WITCH.getValue(), Constants.EntityTypes.Ids.ENTITY_WITCH.getValue(), false),
    WITHER("Wither", Constants.EntityTypes.Names.ENTITY_WITHER.getValue(), Constants.EntityTypes.Ids.ENTITY_WITHER.getValue(), false),
    WOLF("Wolf", Constants.EntityTypes.Names.ENTITY_WOLF.getValue(), Constants.EntityTypes.Ids.ENTITY_WOLF.getValue(), false),
    ZOMBIE("Zombie", Constants.EntityTypes.Names.ENTITY_ZOMBIE.getValue(), Constants.EntityTypes.Ids.ENTITY_ZOMBIE.getValue(), false);

    private final String name;
    private final int id;
    private final Class<? extends ControllableEntity> controllableInterface;
    private final Class<? extends ControllableEntity> controllableClass;
    private final Class<? extends ControllableEntityHandle> handleClass;
    private final boolean isNameRequired;

    ControllableEntityType(String classPath, String name, int id, boolean isNameRequired) {
        this.controllableInterface = new Reflection().reflect("org.entityapi.api.entity.type.Controllable" + classPath).getReflectedClass();
        this.controllableClass = new Reflection().reflect("org.entityapi.api.entity.impl.Controllable" + classPath + "Base").getReflectedClass();
        this.handleClass = new Reflection().reflect(EntityAPI.INTERNAL_NMS_PATH + ".entity.Controllable" + classPath + "Entity").getReflectedClass();
        if (!ControllableEntityHandle.class.isAssignableFrom(handleClass)) {
            throw new RuntimeException("Handle class needs to implement ControllableEntityHandle!");
        }
        this.name = name;
        this.id = id;
        this.isNameRequired = isNameRequired;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public boolean isNameRequired() {
        return this.isNameRequired;
    }

    public <T extends ControllableEntity> Class<T> getControllableInterface() {
        return (Class<T>) this.controllableInterface;
    }

    public <T extends ControllableEntity> Class<T> getControllableClass() {
        return (Class<T>) this.controllableClass;
    }

    public <T extends ControllableEntityHandle> Class<T> getHandleClass() {
        return (Class<T>) this.handleClass;
    }

    public static ControllableEntityType getByControllableClass(Class<? extends ControllableEntity> clazz) {
        for (ControllableEntityType type : values()) {
            if (type.getHandleClass().equals(clazz)) {
                return type;
            }
        }
        return null;
    }

    public static ControllableEntityType getByEntityClass(Class clazz) {
        for (ControllableEntityType type : values()) {
            if (type.getControllableClass().equals(clazz) || type.getControllableClass().getSuperclass().equals(clazz) || type.getControllableClass().isAssignableFrom(clazz)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
