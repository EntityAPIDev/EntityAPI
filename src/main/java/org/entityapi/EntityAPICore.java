package org.entityapi;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.IBasicEntityUtil;
import org.entityapi.api.ISpawnUtil;
import org.entityapi.api.events.*;
import org.entityapi.reflection.SafeConstructor;
import org.entityapi.server.*;
import org.entityapi.utils.PastebinReporter;
import org.entityapi.utils.ReflectionUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class EntityAPICore extends JavaPlugin {

    /**
     * EntityAPI instance
     */
    private static EntityAPICore CORE_INSTANCE;

    private static ISpawnUtil SPAWN_UTIL;
    private static IBasicEntityUtil BASIC_ENTITY_UTIL;

    /**
     * The Server brand
     */
    public static Server SERVER;

    private static final String VERSION = "${project.version}";

    /**
     * Several Loggers
     */
    public static final ModuleLogger LOGGER = new ModuleLogger("EntityAPI");
    public static final ModuleLogger LOGGER_REFLECTION = LOGGER.getModule("Reflection");
    public static final ModuleLogger LOGGER_DATA_STORE = LOGGER_REFLECTION.getModule("Persistence");

    /**
     * Projects id and Pastebin API-KEY
     */
    private static final String UPDATE_ID = "";    // TODO: insert the project id here
    private static final String PASTEBIN_REPORT_KEY = "8759cf9327f8593508789ecaa36cf27b";

    /**
     * Pastebin reporter
     */
    private static final PastebinReporter REPORTER = new PastebinReporter(PASTEBIN_REPORT_KEY);

    /**
     * Plugin files list for checking more than 1 Lib
     */
    private final List<String> plugins = new ArrayList<>();

    @Override
    public void onDisable() {
        //TODO: nullify everything
    }

    @Override
    public void onEnable() {
        if (CORE_INSTANCE != null) {
            throw new RuntimeException("Only one instance of the core can run!");
        }

        CORE_INSTANCE = this;
        SPAWN_UTIL = new SafeConstructor<ISpawnUtil>(ReflectionUtil.getVersionedClass("SpawnUtil")).newInstance();
        BASIC_ENTITY_UTIL = new SafeConstructor<IBasicEntityUtil>(ReflectionUtil.getVersionedClass("BasicEntityUtil")).newInstance();

        initServer();

        this.getPlugins();
        //TODO configuration, Metrics, etc
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
                SERVER = server;
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

    public static EntityAPICore getCore() {
        if (CORE_INSTANCE == null) {
            throw new RuntimeException("The EntityAPICore might have not been initialized properly!");
        }
        return CORE_INSTANCE;
    }

    public static String getVersion() {
        return VERSION;
    }

    /**
     * Api methods
     */
    public final Map<String, EntityManager> MANAGERS = Maps.newHashMap();

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

        EntityManager manager = new SimpleEntityManager(owningPlugin, keepInMemory);
        registerManager(owningPlugin.getName(), manager);

        return manager;
    }

    public static void registerManager(String name, EntityManager manager) {
        EntityAPICore.getCore();

        getCore().addManager(name, manager);
    }

    public static boolean hasEntityManager(Plugin plugin) {
        return hasEntityManager(plugin.getName());
    }

    public static boolean hasEntityManager(String pluginName) {
        return getCore().MANAGERS.containsKey(pluginName);
    }

    public static EntityManager getManagerFor(Plugin plugin) {
        return getManagerFor(plugin.getName());
    }

    public static EntityManager getManagerFor(String pluginName) {
        EntityAPICore.getCore();

        if (!hasEntityManager(pluginName))
            return null;

        return getCore().MANAGERS.get(pluginName);
    }

    /**
     * Method that checks all plugin file names
     */
    protected void getPlugins() {
        PluginManager pm = this.getServer().getPluginManager();
        File dir = new File(this.getDataFolder().getParent());
        for (File pluginFiles : dir.listFiles()) {
            if (dir.isDirectory()) {
                continue;
            }
            if (pluginFiles.getName().toLowerCase().contains("entityapi")) {
                plugins.add(pluginFiles.getName());
            }
        }
        if (plugins.size() > 1) {
            pm.disablePlugin(this);
            this.getLogger().log(Level.SEVERE, "Warning! You have two EntityAPI Libraries in Plugins Folder! Please remove one!");
        }
    }

    @EventHandler
    protected void onPluginDisable(PluginDisableEvent event) {
        if (hasEntityManager(event.getPlugin())) {
            EntityManager entityManager = getManagerFor(event.getPlugin());


        }
    }

    public static ISpawnUtil getSpawnUtil() {
        return SPAWN_UTIL;
    }

    public static IBasicEntityUtil getBasicEntityUtil() {
        return BASIC_ENTITY_UTIL;
    }

    public static void callOnTick(ControllableEntity controllableEntity) {
        ControllableEntityTickEvent tickEvent = new ControllableEntityTickEvent(controllableEntity);
        Bukkit.getServer().getPluginManager().callEvent(tickEvent);
    }

    public static boolean callOnInteract(ControllableEntity controllableEntity, Player entity, boolean rightClick) {
        ControllableEntityInteractEvent interactEvent = new ControllableEntityInteractEvent(controllableEntity, entity, rightClick ? Action.RIGHT_CLICK : Action.LEFT_CLICK);
        Bukkit.getServer().getPluginManager().callEvent(interactEvent);
        return !interactEvent.isCancelled();
    }

    public static Vector callOnPush(ControllableEntity controllableEntity, double x, double y, double z) {
        ControllableEntityPushEvent pushEvent = new ControllableEntityPushEvent(controllableEntity, new Vector(x, y, z));
        Bukkit.getServer().getPluginManager().callEvent(pushEvent);
        return pushEvent.getPushVelocity();
    }

    public static boolean callOnCollide(ControllableEntity controllableEntity, Entity entity) {
        ControllableEntityCollideEvent collideEvent = new ControllableEntityCollideEvent(controllableEntity, entity);
        Bukkit.getServer().getPluginManager().callEvent(collideEvent);
        return !collideEvent.isCancelled();
    }

    public static void callOnDeath(ControllableEntity controllableEntity) {
        ControllableEntityDeathEvent deathEvent = new ControllableEntityDeathEvent(controllableEntity);
        Bukkit.getServer().getPluginManager().callEvent(deathEvent);
        controllableEntity.getMind().setControllableEntity(null);
    }
}
