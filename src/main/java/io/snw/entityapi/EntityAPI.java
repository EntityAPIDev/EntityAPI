package io.snw.entityapi;

import org.bukkit.plugin.Plugin;


public class EntityAPI {

    public static EntityManager createManager(Plugin owningPlugin) {
        return EntityAPICore.createManager(owningPlugin);
    }

    public static EntityManager createEntityManager(Plugin owningPlugin, boolean keepInMemory) {
        return EntityAPICore.createEntityManager(owningPlugin, keepInMemory);
    }

    public static void registerManager(String name, EntityManager manager) {
        EntityAPICore.registerManager(name, manager);
    }

    public static boolean hasEntityManager(Plugin plugin) {
        return EntityAPICore.hasEntityManager(plugin);
    }

    public static boolean hasEntityManager(String pluginName) {
        return EntityAPICore.hasEntityManager(pluginName);
    }

    public static EntityManager getManagerFor(Plugin plugin) {
        return EntityAPICore.getManagerFor(plugin);
    }

    public static EntityManager getManagerFor(String pluginName) {
        return EntityAPICore.getManagerFor(pluginName);
    }
}
