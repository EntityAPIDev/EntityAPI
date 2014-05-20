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

package org.entityapi.reflection.refs;

import org.bukkit.World;
import org.entityapi.reflection.*;

public class CraftWorldRef {

    public static final ClassTemplate CRAFT_WORLD = CBClassTemplate.create("CraftWorld");
    public static final MethodAccessor<Object> TO_WORLDSERVER = CRAFT_WORLD.getMethod("getHandle");

    public static final ClassTemplate WORLD = NMSClassTemplate.create("World");
    public static final FieldAccessor WORLD_PROVIDER = WORLD.getField("worldProvider");
    public static final MethodAccessor<Boolean> ADD_ENTITY = WORLD.getMethod("addEntity", NMSClassTemplate.create("Entity").getType());

    public static Object toNMSWorld(World world) { //returns the worldServer
        return TO_WORLDSERVER.invoke(world);
    }

    public static Object getWorldProvider(World world) { //returns the WorldProvider of a bukkit world.
        return WORLD_PROVIDER.get(toNMSWorld(world));
    }

    public static boolean addEntity(World world, Object NMSEntityClazz) {
        return ADD_ENTITY.invoke(toNMSWorld(world), NMSEntityClazz);
    }
}
