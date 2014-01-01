package io.snw.entityapi;

import io.snw.entityapi.entity.ControllableBatEntity;
import io.snw.entityapi.metrics.Metrics;
import io.snw.entityapi.api.EntityManager;
import io.snw.entityapi.internal.Constants;
import io.snw.entityapi.server.*;
import io.snw.entityapi.utils.EntityUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityAPI extends JavaPlugin {  //abstract classes can't be cast to Plugin so yeah :/

    public static final ModuleLogger LOGGER = new ModuleLogger("EntityAPI");
    public static final ModuleLogger LOGGER_REFLECTION = LOGGER.getModule("Reflection");

    private static EntityAPI INSTANCE;

    public static Server SERVER;

    // do we need this?
    //To check if another instance is already running. Don't want 2 versions of the API running.
    public static Boolean hasInstance() { // why are we using a primitive wrapper here? /captain doesn't get it ._.
        return INSTANCE != null;
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
        registerEntities();
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
            return;
        } else {
            if(!SERVER.isCompatible()) {
                LOGGER.warning("This Server version may not be compatible with EntityAPI!");
            }
            LOGGER.info("Identified server brand: " + SERVER.getName());
            LOGGER.info("MC Version: " + SERVER.getMCVersion());
        }
    }

    protected void registerEntities() {
        EntityUtil.registerEntity(ControllableBatEntity.class, Constants.EntityTypes.Names.ENTITY_BAT, Constants.EntityTypes.Ids.ENTITY_BAT);
    }
    
    /**
     * Will Check for instance of this API running. 
     * If one is found its returned otherwise if not, throws error.
     * @return
     */

    public static EntityAPI getInstance() {
        if (INSTANCE == null) {
            throw new RuntimeException("EntityAPI not Enabled, instance could not be found!");
        }
        return INSTANCE;
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
