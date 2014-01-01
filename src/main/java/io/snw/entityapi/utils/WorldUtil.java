package io.snw.entityapi.utils;

import io.snw.entityapi.reflection.refs.CraftWorldRef;
import org.bukkit.World;


public class WorldUtil {

    public static Object getHandle(World world) {
        return CraftWorldRef.toNMSWorld(world);
    }

    public static Object getWorldServer(World world) {
        return CraftWorldRef.getWorldProvider(world);
    }
}
