package org.entityapi.api.pathfinding.astar;

import org.entityapi.api.ControllableEntity;

public class Pathfinder {

    private final ControllableEntity controllableEntity;
    private boolean isAsync;

    public Pathfinder(ControllableEntity controllableEntity) {
        this(controllableEntity, true);
    }

    public Pathfinder(ControllableEntity controllableEntity, boolean async) {
        this.controllableEntity = controllableEntity;
        this.isAsync = async;
    }
}
