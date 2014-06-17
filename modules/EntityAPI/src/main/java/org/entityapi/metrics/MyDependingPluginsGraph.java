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

package org.entityapi.metrics;

import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.accessor.FieldAccessor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
import org.entityapi.api.utils.LogicUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Graph implementation for showing all the plugins that depend on 'me' as a plugin.
 * Checks for both soft and regular depending.
 */
public class MyDependingPluginsGraph extends Graph {

    private static final FieldAccessor<Collection<Plugin>> pluginsField = (FieldAccessor<Collection<Plugin>>) new Reflection().reflect(SimplePluginManager.class).getSafeFieldByNameAndType("plugins", Collection.class);

    public MyDependingPluginsGraph() {
        this("Depending on me");
    }

    public MyDependingPluginsGraph(final String name) {
        super(name);
    }

    @Override
    protected void onUpdate(Plugin plugin) {
        clearPlotters();
        synchronized (Bukkit.getPluginManager()) {
            for (Plugin otherPlugin : this.getPluginsUnsafe()) {
                if (!otherPlugin.isEnabled()) {
                    continue;
                }
                if (!this.isDepending(otherPlugin, plugin) && !this.isSoftDepending(otherPlugin, plugin)) {
                    continue;
                }
                togglePlotter(otherPlugin.getName(), true);
            }
        }
    }

    public static Collection<Plugin> getPluginsUnsafe() {
        final PluginManager man = Bukkit.getPluginManager();
        if (man instanceof SimplePluginManager) {
            return pluginsField.get(man);
        } else {
            return Arrays.asList(man.getPlugins());
        }
    }

    public static boolean isDepending(Plugin plugin, Plugin depending) {
        final List<String> dep = plugin.getDescription().getDepend();
        return !LogicUtil.nullOrEmpty(dep) && dep.contains(depending.getName());
    }

    public static boolean isSoftDepending(Plugin plugin, Plugin depending) {
        final List<String> dep = plugin.getDescription().getSoftDepend();
        return !LogicUtil.nullOrEmpty(dep) && dep.contains(depending.getName());
    }

}
