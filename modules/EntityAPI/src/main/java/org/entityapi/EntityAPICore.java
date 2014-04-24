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

package org.entityapi;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.entityapi.api.EntityManager;
import org.entityapi.api.IBasicEntityUtil;
import org.entityapi.api.ISpawnUtil;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.events.*;
import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.api.plugin.IEntityAPICore;
import org.entityapi.api.plugin.ModuleLogger;
import org.entityapi.api.plugin.Server;
import org.entityapi.api.reflection.SafeConstructor;
import org.entityapi.api.utils.PastebinReporter;
import org.entityapi.api.utils.ReflectionUtil;
import org.entityapi.api.utils.StringUtil;
import org.entityapi.metrics.Metrics;
import org.entityapi.server.CraftBukkitServer;
import org.entityapi.server.MCPCPlusServer;
import org.entityapi.server.SpigotServer;
import org.entityapi.server.UnknownServer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class EntityAPICore extends JavaPlugin implements IEntityAPICore {

    /**
     * Several Loggers
     */
    public static final ModuleLogger LOGGER = new ModuleLogger("EntityAPI");
    public static final ModuleLogger LOGGER_REFLECTION = LOGGER.getModule("Reflection");
    public static final ModuleLogger LOGGER_DATA_STORE = LOGGER_REFLECTION.getModule("Persistence");
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
     * Projects id and Pastebin API-KEY
     */
    private static final int UPDATE_ID = 69021;
    private static final String PASTEBIN_REPORT_KEY = "8759cf9327f8593508789ecaa36cf27b";

    /**
     * Pastebin reporter
     */
    private static final PastebinReporter REPORTER = new PastebinReporter(PASTEBIN_REPORT_KEY);

    /**
     * Plugin files list for checking more than 1 Lib
     */
    private final List<String> plugins = new ArrayList<>();

    /**
     * Update checker stuff
     */
    public boolean updateAvailable = false;
    public String updateName = "";
    public boolean updateChecked = false;

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
        EntityAPI.setCore(CORE_INSTANCE);
        SPAWN_UTIL = new SafeConstructor<ISpawnUtil>(ReflectionUtil.getVersionedClass("SpawnUtil")).newInstance();
        BASIC_ENTITY_UTIL = new SafeConstructor<IBasicEntityUtil>(ReflectionUtil.getVersionedClass("BasicEntityUtil")).newInstance();

        initServer();

        this.checkPlugins();

        this.saveDefaultConfig();

        try {
            Class.forName(ReflectionUtil.COMPAT_NMS_PATH + ".SpawnUtil");
        } catch (ClassNotFoundException e) {
            ConsoleCommandSender console = this.getServer().getConsoleSender();
            console.sendMessage(ChatColor.RED + "-------------------------------");
            console.sendMessage(ChatColor.RED + "EntityAPI " + ChatColor.GOLD + this.getDescription().getVersion() + ChatColor.RED + " is not compatible with this version of CraftBukkit");
            console.sendMessage(ChatColor.RED + "-------------------------------");
            return;
        }

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            LOGGER.severe("Failed to initialise Metrics");
        }

        this.checkUpdates();
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

    protected void checkUpdates() {
        if (this.getConfig().getBoolean("checkForUpdates", true)) {
            final File file = this.getFile();
            final Updater.UpdateType updateType = this.getConfig().getBoolean("autoUpdate", false) ? Updater.UpdateType.DEFAULT : Updater.UpdateType.NO_DOWNLOAD;
            getServer().getScheduler().runTaskAsynchronously(this, new Runnable() {
                @Override
                public void run() {
                    Updater updater = new Updater(EntityAPI.getCore(), UPDATE_ID, file, updateType, false);
                    updateAvailable = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;
                    if (updateAvailable) {
                        updateName = updater.getLatestName();
                        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "An update is available: " + updateName);
                        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "Type /entityapi update to update.");
                        if (!updateChecked) {
                            updateChecked = true;
                        }
                    }
                }
            });
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("entityapi")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GOLD + "EntityAPI v" + this.getDescription().getVersion() + " by " + StringUtil.join(this.getDescription().getAuthors(), ", "));
                return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("update")) {
                    if (sender.hasPermission("entityapi.update")) {
                        if (updateChecked) {
                            new Updater(this, 74914, this.getFile(), Updater.UpdateType.NO_VERSION_CHECK, true);
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.GOLD + "An update is not available.");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static EntityAPICore getCore() {
        if (CORE_INSTANCE == null) {
            throw new RuntimeException("The EntityAPICore might have not been initialized properly!");
        }
        return CORE_INSTANCE;
    }

    @Override
    public Server getAPIServer() {
        return SERVER;
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public FileConfiguration getConfig(ConfigType type) {
        switch (type) {
            default:
                return this.getConfig();
        }
    }

    /**
     * Api methods
     */
    public final Map<String, EntityManager> MANAGERS = Maps.newHashMap();

    private void addManager(String name, EntityManager entityManager) {
        EntityAPICore.getCore();
        MANAGERS.put(name, entityManager);
    }

    @Override
    public EntityManager createManager(Plugin owningPlugin) {
        EntityAPICore.getCore();
        return createEntityManager(owningPlugin, false);
    }

    @Override
    public EntityManager createEntityManager(Plugin owningPlugin, boolean keepInMemory) {
        EntityAPICore.getCore();

        EntityManager manager = new SimpleEntityManager(owningPlugin, keepInMemory);
        registerManager(owningPlugin.getName(), manager);

        return manager;
    }

    @Override
    public void registerManager(String name, EntityManager manager) {
        EntityAPICore.getCore();

        getCore().addManager(name, manager);
    }

    @Override
    public boolean hasEntityManager(Plugin plugin) {
        return hasEntityManager(plugin.getName());
    }

    @Override
    public boolean hasEntityManager(String pluginName) {
        return getCore().MANAGERS.containsKey(pluginName);
    }

    @Override
    public EntityManager getManagerFor(Plugin plugin) {
        return getManagerFor(plugin.getName());
    }

    @Override
    public EntityManager getManagerFor(String pluginName) {
        EntityAPICore.getCore();

        if (!hasEntityManager(pluginName))
            return null;

        return getCore().MANAGERS.get(pluginName);
    }

    /**
     * Method that checks all plugin file names
     */
    protected void checkPlugins() {
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

    @Override
    public ISpawnUtil getSpawnUtil() {
        return SPAWN_UTIL;
    }

    @Override
    public IBasicEntityUtil getBasicEntityUtil() {
        return BASIC_ENTITY_UTIL;
    }

    @Override
    public void callOnTick(ControllableEntity controllableEntity) {
        ControllableEntityTickEvent tickEvent = new ControllableEntityTickEvent(controllableEntity);
        Bukkit.getServer().getPluginManager().callEvent(tickEvent);
    }

    @Override
    public boolean callOnInteract(ControllableEntity controllableEntity, Player entity, boolean rightClick) {
        ControllableEntityInteractEvent interactEvent = new ControllableEntityInteractEvent(controllableEntity, entity, rightClick ? Action.RIGHT_CLICK : Action.LEFT_CLICK);
        Bukkit.getServer().getPluginManager().callEvent(interactEvent);
        return !interactEvent.isCancelled();
    }

    @Override
    public Vector callOnPush(ControllableEntity controllableEntity, double x, double y, double z) {
        ControllableEntityPushEvent pushEvent = new ControllableEntityPushEvent(controllableEntity, new Vector(x, y, z));
        pushEvent.setCancelled(controllableEntity.isStationary());
        Bukkit.getServer().getPluginManager().callEvent(pushEvent);
        if (pushEvent.isCancelled()) {
            return new Vector(0, 0, 0);
        }
        return pushEvent.getPushVelocity();
    }

    @Override
    public boolean callOnCollide(ControllableEntity controllableEntity, Entity entity) {
        ControllableEntityCollideEvent collideEvent = new ControllableEntityCollideEvent(controllableEntity, entity);
        Bukkit.getServer().getPluginManager().callEvent(collideEvent);
        return !collideEvent.isCancelled();
    }

    @Override
    public void callOnDeath(ControllableEntity controllableEntity) {
        ControllableEntityDeathEvent deathEvent = new ControllableEntityDeathEvent(controllableEntity);
        Bukkit.getServer().getPluginManager().callEvent(deathEvent);
        controllableEntity.getMind().setControllableEntity(null);
    }
}
