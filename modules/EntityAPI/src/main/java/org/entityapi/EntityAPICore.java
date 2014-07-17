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

package org.entityapi;

import com.google.common.collect.Maps;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.DespawnReason;
import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.api.plugin.IEntityAPICore;
import org.entityapi.api.utils.PastebinReporter;
import org.entityapi.api.utils.StringUtil;
import org.entityapi.metrics.Metrics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class EntityAPICore extends JavaPlugin implements IEntityAPICore {

    /**
     * EntityAPI instance
     */
    private static EntityAPICore CORE_INSTANCE;

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
        CORE_INSTANCE = null;
    }

    @Override
    public void onEnable() {
        if (CORE_INSTANCE != null) {
            throw new RuntimeException("Only one instance of the core can run!");
        }

        try {
            // Check if EntityAPI supports this server version
            Class.forName(EntityAPI.INTERNAL_NMS_PATH + ".NMSEntityUtil");
        } catch (ClassNotFoundException e) {
            // Nope! Stop here.
            ConsoleCommandSender console = this.getServer().getConsoleSender();
            console.sendMessage(ChatColor.RED + "-------------------------------");
            console.sendMessage(ChatColor.RED + "EntityAPI " + ChatColor.GOLD + this.getDescription().getVersion() + ChatColor.RED + " is not compatible with this version of CraftBukkit");
            console.sendMessage(ChatColor.RED + "-------------------------------");
            return;
        }

        CORE_INSTANCE = this;
        EntityAPI.setCore(CORE_INSTANCE);

        this.checkPlugins();

        this.saveDefaultConfig();

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            // Swallow
        }

        this.checkUpdates();
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
        return createManager(owningPlugin, false);
    }

    @Override
    public EntityManager createManager(Plugin owningPlugin, boolean keepInMemory) {
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

        if (!hasEntityManager(pluginName)) {
            return null;
        }

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
            entityManager.despawnAll(DespawnReason.PLUGIN_DISABLE);
        }
    }
}
