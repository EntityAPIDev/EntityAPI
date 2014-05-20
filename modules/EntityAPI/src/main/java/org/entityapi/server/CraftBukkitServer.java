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

package org.entityapi.server;

import org.bukkit.Bukkit;
import org.entityapi.api.internal.Constants;
import org.entityapi.api.plugin.Server;
import org.entityapi.api.reflection.refs.MinecraftServerRef;
import org.entityapi.api.utils.ReflectionUtil;

public class CraftBukkitServer implements Server {

    @Override
    public boolean init() {
        String serverPath = Bukkit.getServer().getClass().getName();

        if (!serverPath.startsWith(Constants.Server.CRAFBUKKIT_ROOT.getValue())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean postInit() {
        return false;
    }

    @Override
    public String getName() {
        return "CraftBukkit";
    }

    @Override
    public Object getMinecraftServer() {
        return MinecraftServerRef.getServer(Bukkit.getServer());
    }

    @Override
    public boolean isCompatible() {
        try {
            Class.forName(ReflectionUtil.COMPAT_NMS_PATH + ".SpawnUtil");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
        //return (Constants.Server.SUPPORTED_VERSION_NUMERIC == MC_VERSION_NUMERIC);
    }
}
