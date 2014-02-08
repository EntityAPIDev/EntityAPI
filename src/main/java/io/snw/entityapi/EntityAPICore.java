package io.snw.entityapi;

import io.snw.entityapi.server.*;
import io.snw.entityapi.utils.PastebinReporter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class EntityAPICore extends JavaPlugin {

    /**
     * EntityAPI instance
     */
    private static EntityAPI ENTITY_API_INSTANCE;
    private static EntityAPICore CORE_INSTANCE;

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

    /**
     * Projects id and Pastebin API-KEY
     */
    private static final String UPDATE_ID = "";    // TODO: insert the project id here
    private static final String PASTEBIN_REPORT_KEY = "8759cf9327f8593508789ecaa36cf27b";

    /**
     * Pastebin reporter
     */
    private static final PastebinReporter REPORTER = new PastebinReporter(PASTEBIN_REPORT_KEY);

    @Override
    public void onDisable() {
        //TODO: nullify everything
    }

    @Override
    public void onEnable() {
        if(CORE_INSTANCE != null || ENTITY_API_INSTANCE != null) {
            throw new RuntimeException("Only one instance of the core can run!");
        }

        CORE_INSTANCE = this;

        ENTITY_API_INSTANCE = new EntityAPI(CORE_INSTANCE);
        Bukkit.getPluginManager().registerEvents(ENTITY_API_INSTANCE, this);

        initServer();

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
        if(CORE_INSTANCE == null) {
            throw new RuntimeException("The EntityAPICore might have not been initialized properly!");
        }
        return CORE_INSTANCE;
    }

    public static EntityAPI getEntityAPI() {
        return ENTITY_API_INSTANCE;
    }

    public static String getVersion() {
        return VERSION;
    }
}
