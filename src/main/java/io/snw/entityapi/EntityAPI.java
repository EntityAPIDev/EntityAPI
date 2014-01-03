package io.snw.entityapi;

import io.snw.entityapi.api.EntityManager;
import io.snw.entityapi.entity.ControllableBatEntity;
import io.snw.entityapi.hooks.ChunkProviderServerHook;
import io.snw.entityapi.internal.Constants;
import io.snw.entityapi.metrics.Metrics;
import io.snw.entityapi.server.*;
import io.snw.entityapi.utils.EntityUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;


public abstract class EntityAPI extends JavaPlugin {

    public static final ModuleLogger LOGGER = new ModuleLogger("EntityAPI");
    public static final ModuleLogger LOGGER_REFLECTION = LOGGER.getModule("Reflection");

    private static EntityAPI INSTANCE;

    public static Server SERVER;
    public static HashMap<Plugin, Integer> counters = new HashMap<>();
    public static ArrayList<Plugin> plugins = new ArrayList<>();

    // TODO: This really needs to be redone. I can't see this working very well in its current state :\
    // -> We need to have a talk on what we're doing here so that it can be fixed
    // - DSH

    @Override
    public void onEnable() {
        INSTANCE = this;

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        initServer();
        registerEntities();

        /** DEBUG */
        for (World world : Bukkit.getWorlds()) {
            ChunkProviderServerHook.hook(world);
        }

        if (plugins.size() > 1) { // wat. "plugins" will always be empty, as nothing is ever added to it...
            int index = 0;
            PluginManager pm = this.getServer().getPluginManager();
            pm.disablePlugin(plugins.get(index));
            while (plugins.iterator().hasNext()) {
                pm.disablePlugin(plugins.get(index++));
            }
            this.getLogger().log(Level.SEVERE, "Warning! You have two EntityAPI Libraries in Plugins Folder! Please remove one!");
            //pm.disablePlugin(this);
            //pm.disablePlugin(INSTANCE);
        }
    }

    @Override
    public void onDisable() {
        /** DEBUG */
        for (World world : Bukkit.getWorlds()) {
            ChunkProviderServerHook.unhook(world);
        }
    }

    /**
     * Checks the server brand etc. Also some servers brands don't have the version system (eg: MCPC+) so we need
     * to know that for our reflection.
     */
    protected void initServer() {
        List<Server> servers = new ArrayList<Server>();
        servers.add(new MCPCPlusServer());
        servers.add(new SpigotServer());
        servers.add(new CraftBukkitServer());
        servers.add(new UnknownServer());

        for (Server server : servers) {
            if (server.init()) {   //the first server type that returns true on init is a valid server brand.
                this.SERVER = server;
                break;
            }
        }

        if (SERVER == null) {
            LOGGER.warning("Failed to identify the server brand! The API will not run correctly -> disabling");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        } else {
            if (!SERVER.isCompatible()) {
                LOGGER.warning("This Server version may not be compatible with EntityAPI!");
            }
            LOGGER.info("Identified server brand: " + SERVER.getName());
            LOGGER.info("MC Version: " + SERVER.getMCVersion());
        }
    }

    protected void registerEntities() {
        EntityUtil.registerEntity(ControllableBatEntity.class, Constants.EntityTypes.Names.ENTITY_BAT, Constants.EntityTypes.Ids.ENTITY_BAT);
    }

    public static List<Plugin> compareInstances(JavaPlugin compareTo) { // Where is this used...? -> DSH doesn't get it :\
        if (counters.containsKey(compareTo)) {
            counters.put(compareTo, counters.get(compareTo) + 1);
        } else {
            counters.put(compareTo, 1);
        }
        plugins.add(compareTo);
        return plugins;
    }

    //To check if another instance is already running. Don't want 2 versions of the API running.
    public static boolean hasInstance() {
        return INSTANCE != null;
    }

    /**
     * Will Check for instance of this API running.
     * If one is found its returned otherwise if not, throws error.
     *
     * @return
     */
    public static EntityAPI getInstance() {
        if (INSTANCE == null) {
            throw new RuntimeException("EntityAPI not Enabled, instance could not be found!");
        }
        return INSTANCE;
    }

    public <T extends Event> T callEvent(T event) {
        Bukkit.getServer().getPluginManager().callEvent(event);
        return event;
    }

    /**
     * Will return a unique entity manager for each plugin using this.
     * (to have some cross plugin working version stuff)
     * I'm not really experienced with this and the EntityManager is just an idea
     * you guys can do whatever you want with it since I don't really know what your original
     * idea was :/
     * (captainbern)
     *
     * @param plugin
     * @return
     */
    public EntityManager getEntityManager(Plugin plugin) {
        return null;
    }
}
