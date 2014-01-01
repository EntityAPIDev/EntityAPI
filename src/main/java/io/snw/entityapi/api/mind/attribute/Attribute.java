package io.snw.entityapi.api.mind.attribute;

import io.snw.entityapi.api.ControllableEntity;
import io.snw.entityapi.api.mind.Mind;

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