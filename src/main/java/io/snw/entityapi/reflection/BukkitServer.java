package io.snw.entityapi.reflection;

import org.bukkit.Bukkit;

public class BukkitServer {

    public static final String NMS_ROOT = getNMSPackageName();
    public static final String CB_ROOT = getOBCPackageName();

    private static String getNMSPackageName() {
        return "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    private static String getOBCPackageName(){
        return "org.bukkit.craftbukkit" + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    public static Class<?> getClass(String path){
        try{
            return Class.forName(path);
        }catch(Exception e){
            return null;
        }
    }

    public static Class<?> getNMSClass(String className){
        return getClass(BukkitServer.NMS_ROOT + "." + className);
    }

    public static Class<?> getCBClass(String className){
        return getClass(BukkitServer.CB_ROOT + "." + className);
    }
}
