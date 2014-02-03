package io.snw.entityapi.api.pathfinding.astar;

import io.snw.entityapi.api.ControllableEntity;

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
