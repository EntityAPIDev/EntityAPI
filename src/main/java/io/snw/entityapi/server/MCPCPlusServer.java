package io.snw.entityapi.server;

import org.bukkit.Bukkit;

public class MCPCPlusServer extends SpigotServer {

    @Override
    public boolean init() {
        if (!super.init() || !Bukkit.getServer().getVersion().contains("MCPC-Plus")) {
            return false;
        }

        this.MINECRAFT_VERSIONED = "net.minecraft.server";  // in mcpc+ there is no versioning :/

        return true;
    }

    @Override
    public String getName() {
        return "MCPC+";
    }
}
