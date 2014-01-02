/**
 * For more info: http://www.policyalmanac.org/games/aStarTutorial.htm
 */
package io.snw.entityapi.api.pathfinding.astar;

import org.bukkit.Location;
import org.bukkit.World;

public class BlockNode {

    private Pathfinder pathfinder;
    private Location location;

    public static double COST_NORMAL = 1.0d;
    public static double COST_UP = 1.1d;
    public static double COST_DIAGONAL = 1.6d;
    public static double COST_DIAGONAL_UP = 1.8d;

    public BlockNode(Pathfinder pathfinder, Location location) {
        this.pathfinder = pathfinder;
        this.location = location;
    }

    public int getX() {
        return this.location.getBlockX();
    }

    public int getY() {
        return this.location.getBlockY();
    }

    public int getZ() {
        return this.location.getBlockZ();
    }

    public World getWorld() {
        return this.location.getWorld();
    }

    public Location getLocation() {
        return this.location.clone();
    }

    @Override
    public String toString() {
        return getX() + ":" + getY() + ":" + getZ();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
