package io.snw.entityapi.api;

import io.snw.entityapi.entity.ControllableBaseEntity;
import io.snw.entityapi.entity.ControllableEntityHandle;
import io.snw.entityapi.entity.ControllableBat;
import io.snw.entityapi.entity.ControllableBatEntity;

public enum ControllableEntityType {

    BAT(ControllableBat.class, ControllableBatEntity.class);

    Class<? extends ControllableBaseEntity> entityClass;
    Class<? extends ControllableEntityHandle> handleClass;

    ControllableEntityType(Class<? extends ControllableBaseEntity> entityClass, Class<? extends ControllableEntityHandle> handleClass) {
        this.entityClass = entityClass;
        this.handleClass = handleClass;
    }

    public Class<? extends ControllableBaseEntity> getEntityClass() {
        return entityClass;
    }

    public Class<? extends ControllableEntityHandle> getHandleClass() {
        return handleClass;
    }
}