package io.snw.entityapi.wrapper;

import io.snw.entityapi.EntityAPICore;

public abstract class AbstractWrapper {

    private Object handle;

    public AbstractWrapper() {
    }

    protected void setHandle(Object handle) {
        if (handle == null) {
            EntityAPICore.LOGGER_REFLECTION.warning("Cannot create a wrapper with a null handle!");
            return;
        }
        this.handle = handle;
    }

    public Object getHandle() {
        return this.handle;
    }
}
