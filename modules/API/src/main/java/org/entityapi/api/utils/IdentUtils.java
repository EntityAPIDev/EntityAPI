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

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.entityapi.reflection.utility.CommonReflection;

import java.util.UUID;

public class IdentUtils {

    public static boolean canReturnUUID() {
        try {
            Bukkit.class.getDeclaredMethod("getPlayer", UUID.class);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    public static Object getIdentificationFor(Player player) {
        if (CommonReflection.getNumericVersionTag() >= 172 && canReturnUUID()) {
            return player.getUniqueId();
        } else {
            return player.getName();
        }
    }

    public static String getIdentificationForAsString(Player player) {
        if (CommonReflection.getNumericVersionTag() >= 172 && canReturnUUID()) {
            return player.getUniqueId().toString();
        } else {
            return player.getName();
        }
    }

    public static Player getPlayerOf(Object identification) {
        if (CommonReflection.getNumericVersionTag() >= 172 && canReturnUUID()) {
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