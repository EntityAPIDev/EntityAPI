package io.snw.entityapi.utils;

import io.snw.entityapi.reflection.refs.CraftWorldRef;
import io.snw.entityapi.reflection.refs.WorldServerRef;
import org.bukkit.World;


public class WorldUtil {

    public static Object toWorldServer(World world) {
        return CraftWorldRef.toNMSWorld(world);
    }

    public static Object getChunkProviderServer(World world) {
        return WorldServerRef.getChunkProviderServer(toWorldServer(world));
    }
}
