package io.snw.entityapi;

import org.bukkit.plugin.PluginBase;

import java.io.IOException;

public abstract class EntityAPI extends PluginBase {

    public static final ModuleLogger LOGGER = new ModuleLogger("EntityAPI");
    public static final ModuleLogger LOGGER_REFLECTION = LOGGER.getModule("Reflection");

    private static EntityAPI instance;


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
    }

}
