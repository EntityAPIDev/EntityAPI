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

    private double h_score;
    private double g_score;

    public BlockNode(Pathfinder pathfinder, Location location) {
        this.pathfinder = pathfinder;
        this.location = location;
        this.h_score = -1;
        this.g_score = -1;
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

    public double getHScore() {
        return this.h_score;
    }

    public double getGScore() {
        return this.g_score;
    }

    public double getFScore() {
        return this.h_score + this.g_score;
    }

    public void calculateHScore() {

    }

    public void calculateGScore() {

    }
}
