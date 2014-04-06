/*
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
import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.api.plugin.Server;
import org.entityapi.api.reflection.refs.MinecraftServerRef;
import org.entityapi.api.utils.ReflectionUtil;

public class CraftBukkitServer implements Server {

    public String MC_VERSION;

    public int MC_VERSION_NUMERIC;

    public String CRAFTBUKKIT_VERSIONED;

    public String MINECRAFT_VERSIONED;

    @Override
    public boolean init() {
        String serverPath = Bukkit.getServer().getClass().getName();

        if (!serverPath.startsWith(Constants.Server.CRAFBUKKIT_ROOT.getValue())) {
            return false;
        }

        MC_VERSION = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

        if (MC_VERSION.isEmpty()) {
            CRAFTBUKKIT_VERSIONED = Constants.Server.CRAFBUKKIT_ROOT.getValue();
            MINECRAFT_VERSIONED = Constants.Server.MINECRAFT_ROOT.getValue();
        } else {
            CRAFTBUKKIT_VERSIONED = Constants.Server.CRAFBUKKIT_ROOT.getValue() + "." + MC_VERSION;
            MINECRAFT_VERSIONED = Constants.Server.MINECRAFT_ROOT.getValue() + "." + MC_VERSION;
        }

        MC_VERSION_NUMERIC = Integer.valueOf(MC_VERSION.replaceAll("[^0-9]", ""));

        return true;
    }

    @Override
    public boolean postInit() {
        return false;
    }

    @Override
    public Class getClass(String name) {
        try {
            return Class.forName(name);
        } catch (Exception e) {
            EntityAPI.LOGGER_REFLECTION.warning("Failed to find matching class for: " + name);
            return null;
        }
    }

    @Override
    public Class getNMSClass(String name) {
        return getClass(MINECRAFT_VERSIONED + "." + name);
    }

    @Override
    public Class getCBClass(String name) {
        return getClass(CRAFTBUKKIT_VERSIONED + "." + name);
    }

    @Override
    public String getName() {
        return "CraftBukkit";
    }

    @Override
    public int getVersion() {
        return MC_VERSION_NUMERIC;
    }

    @Override
    public Object getMinecraftServer() {
        return MinecraftServerRef.getServer(Bukkit.getServer());
    }

    @Override
    public String getMCVersion() {
        return MC_VERSION;
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
