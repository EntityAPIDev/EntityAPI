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

package org.entityapi.api.utils;

import com.captainbern.minecraft.conversion.BukkitUnwrapper;
import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeMethod;
import com.captainbern.reflection.accessor.MethodAccessor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;

import static com.captainbern.reflection.matcher.Matchers.withArguments;
import static com.captainbern.reflection.matcher.Matchers.withReturnType;

public class WorldUtil {

    private WorldUtil() {}

    public static Object toNMSWorld(World world) {
        return BukkitUnwrapper.getInstance().unwrap(world);
    }

    protected static MethodAccessor<Boolean> ADD_ENTITY;

    public static boolean addEntity(World world, Object entityHandle, CreatureSpawnEvent.SpawnReason reason) {
        if (ADD_ENTITY == null) {
            ClassTemplate worldTemplate = new Reflection().reflect(MinecraftReflection.getMinecraftClass("World"));

            List<SafeMethod<Boolean>> candidates = worldTemplate.getSafeMethods(withReturnType(boolean.class), withArguments(MinecraftReflection.getEntityClass(), CreatureSpawnEvent.SpawnReason.class));

            if (candidates.size() > 0) {
                ADD_ENTITY = candidates.get(0).getAccessor();
            } else {
                throw new RuntimeException("Failed to get the addEntity method!");
            }
        }

        return ADD_ENTITY.invoke(toNMSWorld(world), entityHandle, reason);
    }

    /**
     * Returns the distance between 2 given locations without going diagonal.
     *
     * @param b1     The first point
     * @param b2     The second point
     * @param checkY Shall it calculate the distance with the Y value included?
     * @return The manhattan distance between 2 given points
     */
    public static int getManhattanDistance(Location b1, Location b2, boolean checkY) {
        int d = Math.abs(b1.getBlockX() - b2.getBlockX());
        d += Math.abs(b1.getBlockZ() - b2.getBlockZ());
        if (checkY)
            d += Math.abs(b1.getBlockY() - b2.getBlockY());
        return d;
    }

    /**
     * Returns the Euclidean distance between 2 given location.
     *
     * @param b1     The first point
     * @param b2     The second point
     * @param checkY Shall it calculate the distance with the Y value included?
     * @return The Euclidean distance between 2 given points.
     */
    public static double getEuclideanDistance(Location b1, Location b2, boolean checkY) {
        double d = Math.sqrt(Math.pow(b1.getX() - b2.getX(), 2) + Math.pow(b1.getZ() - b2.getZ(), 2));
        if (checkY)
            d += Math.sqrt(Math.pow(b1.getY() - b2.getY(), 2));
        return d;
    }
}
