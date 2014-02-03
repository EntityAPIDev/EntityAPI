package io.snw.entityapi.api.pathfinding.astar;

public enum HeuristicType {

    EUCLIDEAN,  // calculates the euclidean distance between 2 points, just looks for closest point.
    MANHATTAN;  // calculates manhattan distance between 2 points. This means it will not go diagonal etc.
}
