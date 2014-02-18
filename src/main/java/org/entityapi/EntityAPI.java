package org.entityapi;

import org.bukkit.plugin.Plugin;

public class EntityAPI {

    /**
     * Creates an EntityManager for the given Plugin.
     *
     * @param owningPlugin The plugin the EntityManager should be assigned to.
     * @return The EntityManager object for the given plugin.
     */
    public static EntityManager createManager(Plugin owningPlugin) {
        return EntityAPICore.createManager(owningPlugin);
    }

    /**
     * Creates an EntityManager for the given Plugin.
     *
     * @param owningPlugin The plugin the EntityManager should be assigned to.
     * @param keepInMemory Whether or not the EntityManager should keep every entity in memory. Even when the entity died.
     * @return The EntityManager for the given plugin.
     */
    public static EntityManager createEntityManager(Plugin owningPlugin, boolean keepInMemory) {
        return EntityAPICore.createEntityManager(owningPlugin, keepInMemory);
    }

    /**
     * Registers an EntityManager with the given name.
     *
     * @param name    The name the EntityManager will be registered with.
     * @param manager The EntityManager.
     */
    public static void registerManager(String name, EntityManager manager) {
        EntityAPICore.registerManager(name, manager);
    }

    /**
     * Returns true if the given plugin has an EntityManager.
     *
     * @param plugin The plugin that should be checked.
     * @return Whether or not the plugin has an EntityManager.
     */
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
