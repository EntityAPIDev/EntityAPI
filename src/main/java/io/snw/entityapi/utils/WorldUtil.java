package io.snw.entityapi.utils;

import io.snw.entityapi.reflection.refs.CraftWorldRef;
import io.snw.entityapi.reflection.refs.WorldServerRef;
import org.bukkit.Location;
import org.bukkit.World;


public class WorldUtil {

    public static Object toWorldServer(World world) {
        return CraftWorldRef.toNMSWorld(world);
    }

    public static int getManhattanDistance(Location b1, Location b2, boolean checkY) {
        int d = Math.abs(b1.getBlockX() - b2.getBlockX());
        d += Math.abs(b1.getBlockZ() - b2.getBlockZ());
        if (checkY)
            d += Math.abs(b1.getBlockY() - b2.getBlockY());
        return d;
    }

    public static double getEuclideanDistance(Location b1, Location b2, boolean checkY) {
        double d = Math.sqrt(Math.pow(b1.getX() - b2.getX(), 2) + Math.pow(b1.getZ() - b2.getZ(), 2));
        if(checkY)
            d += Math.sqrt(Math.pow(b1.getY() - b2.getY(), 2));
        return d;
    }
}
