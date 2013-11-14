package io.snw.entityapi;

import java.io.IOException;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class EntityAPI extends JavaPlugin { 
    

    
    @Override
    public void onEnable(){
        
        try{
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
}
    
}
