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

package org.entityapi.api.plugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntity;

public interface IEntityAPICore extends Plugin {

    public String getVersion();

    public FileConfiguration getConfig(ConfigType type);

    public EntityManager createManager(Plugin owningPlugin);

    public EntityManager createEntityManager(Plugin owningPlugin, boolean keepInMemory);

    public void registerManager(String name, EntityManager manager);

    public boolean hasEntityManager(Plugin plugin);

    public boolean hasEntityManager(String pluginName);

    public EntityManager getManagerFor(Plugin plugin);

    public EntityManager getManagerFor(String pluginName);

    public enum ConfigType {
        MAIN
    }
}