package org.entityapi.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.entityapi.reflection.refs.CraftWorldRef;

public class WorldUtil {

    public static Object toWorldServer(World world) {
        return CraftWorldRef.toNMSWorld(world);
    }

    public static Object toNMSWorld(World world) {
        return CraftWorldRef.toNMSWorld(world);
    }

    /**
     * Returns the distance between 2 given locations without going diagonal.
     *
     * @param b1     The first point
     * @param b2     The second point
     * @param checkY Shall it calculate the distance with the Y value included?
     * @return The manhattan distance between 2 given points
     */
    public static int getManhattanDistance(Location b1, Location b2, boolean checkY) {
        int d = Math.abs(b1.getBlockX() - b2.getBlockX());
        d += Math.abs(b1.getBlockZ() - b2.getBlockZ());
        if (checkY)
            d += Math.abs(b1.getBlockY() - b2.getBlockY());
        return d;
    }

    /**
     * Returns the Euclidean distance between 2 given location.
     *
     * @param b1     The first point
     * @param b2     The second point
     * @param checkY Shall it calculate the distance with the Y value included?
     * @return The Euclidean distance between 2 given points.
     */
    public static double getEuclideanDistance(Location b1, Location b2, boolean checkY) {
        double d = Math.sqrt(Math.pow(b1.getX() - b2.getX(), 2) + Math.pow(b1.getZ() - b2.getZ(), 2));
        if (checkY)
            d += Math.sqrt(Math.pow(b1.getY() - b2.getY(), 2));
        return d;
    }
}
