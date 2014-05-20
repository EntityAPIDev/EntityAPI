/*
 * Copyright (C) EntityAPI Team
 *
 * This file is part of EntityAPI.
 *
 * EntityAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EntityAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EntityAPI.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * For more info: http://www.policyalmanac.org/games/aStarTutorial.htm
 *
 * Some of the code in this class is based off:
 * https://github.com/kumpelblase2/Remote-Entities/blob/master/src/main/java/de/kumpelblase2/remoteentities/api/pathfinding/BlockNode.java
 *
 * And:
 * https://github.com/Adamki11s/AStar-Pathfinding/blob/master/src/com/adamki11s/pathing/Tile.java
 */
package org.entityapi.api.pathfinding.astar;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class BlockNode {

    private Pathfinder pathfinder;
    private Location location;
    private BlockNode parent;

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

    public BlockNode getParent() {
        return this.parent;
    }

    public void setParent(BlockNode parent) {
        this.parent = parent;
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

    public void calculateHScore(Block end) {
        this.h_score = this.pathfinder.getHeuristicType().calculate(getLocation(), end.getLocation(), true);
    }

    public void calculateGScore() {
        BlockNode parent = null;
        BlockNode current = this;

        double g_cost = 0;

        while ((parent = current.parent) != null) {
            int dx = Math.abs(parent.getX() - current.getX());
            int dy = Math.abs(parent.getY() - current.getY());
            int dz = Math.abs(parent.getZ() - current.getZ());

            if (dx == 1 && dz == 1) {
                if (dy == 1) {
                    g_cost += BlockNode.COST_DIAGONAL_UP;
                } else {
                    g_cost += BlockNode.COST_DIAGONAL;
                }
            } else if (dy == 1) {
                g_cost += BlockNode.COST_UP;
            } else {
                g_cost += BlockNode.COST_NORMAL;
            }
            current = parent;
        }

        this.g_score = g_cost;
    }

    public boolean canWalkTrough() {
        return MaterialChecker.isTransparent(this.location.getBlock());
    }

    public boolean canJumpOn() {
        return this.location.getBlock().getRelative(BlockFace.UP, 3).isEmpty();
    }

    @Override
    public String toString() {
        return getX() + ":" + getY() + ":" + getZ();
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ toString().hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof BlockNode) {
            BlockNode node = (BlockNode) object;

            return node.toString().equalsIgnoreCase(this.toString());
        }
        return false;
    }
}
