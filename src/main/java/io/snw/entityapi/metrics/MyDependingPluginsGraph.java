package io.snw.entityapi.metrics;

import io.snw.entityapi.reflection.FieldAccessor;
import io.snw.entityapi.reflection.SafeField;
import io.snw.entityapi.utils.LogicUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Graph implementation for showing all the plugins that depend on 'me' as a plugin.
 * Checks for both soft and regular depending.
 */
public class MyDependingPluginsGraph extends Graph {

    private static final FieldAccessor<Collection<Plugin>> pluginsField = new SafeField<>(SimplePluginManager.class, "plugins");

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
