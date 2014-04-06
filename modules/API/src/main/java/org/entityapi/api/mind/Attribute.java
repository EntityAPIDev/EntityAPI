package org.entityapi.api.mind;

import org.entityapi.api.ControllableEntity;

public abstract class Attribute {

    protected Mind mind;

    public Attribute(Mind mind) {
        this.mind = mind;
    }

    public Mind getMind() {
        return mind;
    }

    public ControllableEntity getControllableEntity() {
        return this.mind.getControllableEntity();
    }

    public abstract String getKey();

    public void tick() {

    }
}