package org.entityapi.api.wrapper;

import org.entityapi.api.plugin.EntityAPI;

public abstract class AbstractWrapper {

    private Object handle;

    public AbstractWrapper() {
    }

    protected void setHandle(Object handle) {
        if (handle == null) {
            EntityAPI.LOGGER_REFLECTION.warning("Cannot create a wrapper with a null handle!");
            return;
        }
        this.handle = handle;
    }

    public Object getHandle() {
        return this.handle;
    }
}
