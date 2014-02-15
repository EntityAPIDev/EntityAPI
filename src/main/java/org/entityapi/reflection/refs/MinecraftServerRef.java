package org.entityapi.reflection.refs;

import org.entityapi.reflection.CBClassTemplate;
import org.entityapi.reflection.ClassTemplate;
import org.entityapi.reflection.MethodAccessor;
import org.entityapi.reflection.NMSClassTemplate;
import org.bukkit.Server;

public class MinecraftServerRef {

    public static final ClassTemplate CRAFTSERVER = CBClassTemplate.create("CraftServer");
    public static final MethodAccessor<Object> CRAFTSERVER_GET_HANDLE = CRAFTSERVER.getMethod("getHandle");

    public static final ClassTemplate MINECRAFTSERVER = NMSClassTemplate.create("MinecraftServer");
    public static final MethodAccessor MINECRAFTSERVER_GET_SERVER = MINECRAFTSERVER.getMethod("getServer");

    public static Object toCraftServer(Server server) {
        return CRAFTSERVER_GET_HANDLE.invoke(server);
    }

    public static Object getServer(Server server) {
        return MINECRAFTSERVER_GET_SERVER.invoke(toCraftServer(server));
    }
}
