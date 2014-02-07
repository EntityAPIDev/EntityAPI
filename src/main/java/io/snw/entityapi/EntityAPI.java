package io.snw.entityapi;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.util.Map;


public class EntityAPI implements Listener {

    private static EntityAPI ENTITY_API_INSTANCE;

    public EntityAPI(EntityAPICore core) {
        if(ENTITY_API_INSTANCE != null) {
            throw new RuntimeException("EntityAPI is already initialized!");
        }
        ENTITY_API_INSTANCE = this;

        Bukkit.getPluginManager().registerEvents(this, core);
    }

    public final Map<String, EntityManager> MANAGERS = Maps.newHashMap();
    /**
     * API STUFF
     */
    private void addManager(String name, EntityManager entityManager) {
        EntityAPICore.getCore();
        MANAGERS.put(name, entityManager);
    }

    public static EntityManager createManager(Plugin owningPlugin) {
        EntityAPICore.getCore();
        return createEntityManager(owningPlugin, false);
    }

    public static EntityManager createEntityManager(Plugin owningPlugin, boolean keepInMemory) {
        EntityAPICore.getCore();

        EntityManager manager = new EntityManager(owningPlugin, keepInMemory);
        registerManager(owningPlugin.getName(), manager);

        return manager;
    }

    public static void registerManager(String name, EntityManager manager) {
        EntityAPICore.getCore();

        ENTITY_API_INSTANCE.addManager(name, manager);
    }

    public static boolean hasEntityManager(Plugin plugin) {
        return hasEntityManager(plugin.getName());
    }

    public static boolean hasEntityManager(String pluginName) {
        return ENTITY_API_INSTANCE.MANAGERS.containsKey(pluginName);
    }

    public static EntityManager getManagerFor(Plugin plugin) {
        return getManagerFor(plugin.getName());
    }

    public static EntityManager getManagerFor(String pluginName) {
        EntityAPICore.getCore();

        if (!hasEntityManager(pluginName))
            return null;

        return ENTITY_API_INSTANCE.MANAGERS.get(pluginName);
    }

    @EventHandler
    public void onDisable(PluginDisableEvent event) {
        // TODO: take care of plugin stuff entity
    }
}
