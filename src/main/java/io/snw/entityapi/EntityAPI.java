package io.snw.entityapi;

import io.snw.entityapi.entity.EntityManager;
import io.snw.entityapi.server.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityAPI extends PluginBase {

    public static final ModuleLogger LOGGER = new ModuleLogger("EntityAPI");
    public static final ModuleLogger LOGGER_REFLECTION = LOGGER.getModule("Reflection");

    private static EntityAPI INSTANCE;

    public static Server SERVER;

    public static Boolean hasInstance() { // why are we using a primitive wrapper here? /captain doesn't get it ._.
        return INSTANCE != null;
    }

    public static EntityAPI getInstance() {
        if (INSTANCE == null) {
            throw new RuntimeException("EntityAPI not Enabled, instance could not be found!");
        }
        return INSTANCE;
    }

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

        for(Server server : servers) {
            if(server.init()) {   //the first server type that returns true on init is a valid server brand.
                this.SERVER = server;
                break;
            }
        }

        if(SERVER == null) {
            LOGGER.warning("Failed to identify the server brand! The API will not run correctly -> disabling");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            if(!SERVER.isCompatible()) {
                LOGGER.warning("This Server version is not compatible with EntityAPI! -> disabling");
                Bukkit.getPluginManager().disablePlugin(this);
            }

            LOGGER.info("Identified server brand: " + SERVER.getName());
            LOGGER.info("MC Version: " + SERVER.getMCVersion());
        }
    }

    /**
     * Will return a unique entity manager for each plugin using this.
     * (to have some cross plugin working version stuff)
     * I'm not really experienced with this and the EntityManager is just an idea
     * you guys can do whatever you want with it since I don't really know what your original
     * idea was :/
     * (captainbern)
     * @param plugin
     * @return
     */
    public EntityManager getEntityManager(Plugin plugin) {
        return null;
    }
}
