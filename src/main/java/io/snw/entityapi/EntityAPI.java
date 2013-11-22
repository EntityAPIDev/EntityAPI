package io.snw.entityapi;

import org.bukkit.plugin.PluginBase;
import org.mcstats.Metrics;

import java.io.IOException;

public abstract class EntityAPI extends PluginBase {

    private static EntityAPI instance;


    public static Boolean hasInstance() {
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
