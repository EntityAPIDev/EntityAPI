package org.entityapi.api.pathfinding.astar;

import org.entityapi.utils.WorldUtil;
import org.bukkit.Location;

public enum HeuristicType {

    EUCLIDEAN {
        @Override
        public double calculate(Location from, Location to, boolean checkY) {
             return WorldUtil.getEuclideanDistance(from, to, checkY);
        }
    },
    MANHATTAN {
        @Override
        public double calculate(Location from, Location to, boolean checkY) {
            return WorldUtil.getManhattanDistance(from, to, checkY);
        }
    };

    public double calculate(Location from, Location to, boolean checkY) {
        throw new RuntimeException("Not (properly?) implemented!");
    }
}
