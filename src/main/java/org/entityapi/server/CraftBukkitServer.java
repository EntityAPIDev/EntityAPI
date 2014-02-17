package org.entityapi.server;

import org.bukkit.Bukkit;
import org.entityapi.EntityAPICore;
import org.entityapi.internal.Constants;
import org.entityapi.reflection.refs.MinecraftServerRef;

public class CraftBukkitServer implements Server {

    public String MC_VERSION;

    public int MC_VERSION_NUMERIC;

    public String CRAFTBUKKIT_VERSIONED;

    public String MINECRAFT_VERSIONED;

    @Override
    public boolean init() {
        String serverPath = Bukkit.getServer().getClass().getName();

        if (!serverPath.startsWith(Constants.Server.CRAFBUKKIT_ROOT)) {
            return false;
        }

        MC_VERSION = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

        if (MC_VERSION.isEmpty()) {
            CRAFTBUKKIT_VERSIONED = Constants.Server.CRAFBUKKIT_ROOT;
            MINECRAFT_VERSIONED = Constants.Server.MINECRAFT_ROOT;
        } else {
            CRAFTBUKKIT_VERSIONED = Constants.Server.CRAFBUKKIT_ROOT + "." + MC_VERSION;
            MINECRAFT_VERSIONED = Constants.Server.MINECRAFT_ROOT + "." + MC_VERSION;
        }

        MC_VERSION_NUMERIC = Integer.valueOf(MC_VERSION.replaceAll("[^0-9]", ""));

        return true;
    }

    @Override
    public boolean postInit() {
        return false;
    }

    @Override
    public Class getClass(String name) {
        try {
            return Class.forName(name);
        } catch (Exception e) {
            EntityAPICore.LOGGER_REFLECTION.warning("Failed to find matching class for: " + name);
            return null;
        }
    }

    @Override
    public Class getNMSClass(String name) {
        return getClass(MINECRAFT_VERSIONED + "." + name);
    }

    @Override
    public Class getCBClass(String name) {
        return getClass(CRAFTBUKKIT_VERSIONED + "." + name);
    }

    @Override
    public String getName() {
        return "CraftBukkit";
    }

    @Override
    public int getVersion() {
        return MC_VERSION_NUMERIC;
    }

    @Override
    public Object getMinecraftServer() {
        return MinecraftServerRef.getServer(Bukkit.getServer());
    }

    @Override
    public String getMCVersion() {
        return MC_VERSION;
    }

    @Override
    public boolean isCompatible() {
        return (Constants.Server.SUPPORTED_VERSION_NUMERIC == MC_VERSION_NUMERIC);
    }
}
