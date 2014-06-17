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

import com.captainbern.minecraft.reflection.MinecraftReflection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class IdentUtils {

    public static boolean supportsUuid() {
        try {
            Bukkit.class.getDeclaredMethod("getPlayer", UUID.class);
        } catch (NoSuchMethodException e) {
            return false;
        }
        return Integer.valueOf(MinecraftReflection.getVersionTag().replaceAll("[^0-9]", "")) >= 172;
    }


    public static Object getIdentificationFor(Player player) {
        if (supportsUuid()) {
            return player.getUniqueId();
        } else {
            return player.getName();
        }
    }

    public static String getIdentificationForAsString(Player player) {
        if (supportsUuid()) {
            return player.getUniqueId().toString();
        } else {
            return player.getName();
        }
    }

    public static Player getPlayerOf(Object identification) {
        if (supportsUuid()) {
            if (identification instanceof UUID) {
                return Bukkit.getPlayer((UUID) identification);
            } else if (identification instanceof String) {
                return Bukkit.getPlayer(UUID.fromString((String) identification));
            }
        } else if (identification instanceof String) {
            return Bukkit.getPlayerExact((String) identification);
        }
        return null;
    }
}