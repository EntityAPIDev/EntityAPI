package org.entityapi.api.pathfinding.astar;

import org.bukkit.Location;
import org.entityapi.api.ControllableEntity;

import java.util.PriorityQueue;
import java.util.Set;

public class Pathfinder {

    private final ControllableEntity controllableEntity;
    private boolean isAsync;

    private PriorityQueue<BlockNode> open_list = new PriorityQueue<>();

    private HeuristicType heuristicType;

    public Pathfinder(ControllableEntity controllableEntity) {
        this(controllableEntity, true);
    }

    public Pathfinder(ControllableEntity controllableEntity, boolean async) {
        this.controllableEntity = controllableEntity;
        this.isAsync = async;
    }

    public boolean isAsync() {
        return this.isAsync;
    }

    public void setAsyn(boolean async) {
        this.isAsync = async;
    }

    public Set<BlockNode> find(Location to) {
        return null;
    }

    public HeuristicType getHeuristicType() {
        return this.heuristicType;
    }
}
