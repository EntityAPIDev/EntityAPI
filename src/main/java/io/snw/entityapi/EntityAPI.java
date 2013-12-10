package io.snw.entityapi;

import io.snw.entityapi.server.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityAPI extends PluginBase {

    public static final ModuleLogger LOGGER = new ModuleLogger("EntityAPI");
    public static final ModuleLogger LOGGER_REFLECTION = LOGGER.getModule("Reflection");

    private static EntityAPI instance;

    public static Server SERVER;

    public static Boolean hasInstance() { // why are we using a primitive wrapper here? /captain doesn't get it ._.
        return instance != null;
    }

    public static EntityAPI getInstance() {
        if (instance == null) {
            throw new RuntimeException("EntityAPI not Enabled, instance could not be found");
        }
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        initServer();
    }

    protected void initServer() {
        List<Server> servers = new ArrayList<Server>();
        servers.add(new MCPCPlusServer());
        servers.add(new SpigotServer());
        servers.add(new CraftBukkitServer());
        servers.add(new UnknownServer());

        for(Server server : servers) {
            if(server.init()) {
                this.SERVER = server;
                break;
            }
        }

        if(SERVER == null) {
            LOGGER.warning("Failed to identify the server brand! The API will not run correctly -> disabling");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }
}
